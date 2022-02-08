package com.example.collapsable.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.collapsable.network.repository.MainRepository
import com.example.collapsable.network.response.product.ProductResponse
import com.example.collapsable.network.response.Response
import com.example.collapsable.network.response.social.SocialResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/** Constructor injection needed since with @HiltViewModel annotation
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {
    //Multiple disposable can be added
    private val compositeDisposable = CompositeDisposable()

    var imageUrl: MutableLiveData<String> = MutableLiveData("")
    var productName: MutableLiveData<String> = MutableLiveData("")
    var isFavourite: MutableLiveData<Boolean> = MutableLiveData(false)

    val socialResponseLMld = MutableLiveData<Response<SocialResponse?>>()
    val productResponseLMld = MutableLiveData<Response<ProductResponse?>>()

    /** This status would trigger like action result with further implementation
     *  Now it just changes like button
     * */
    fun changeLikeStatus() {
        if (this.isFavourite.value == true) {
            this.isFavourite.setValue(false)
        } else {
            this.isFavourite.setValue(true)
        }
    }

    /** Get SocialResponse Using Hilt Rxjava Retrofit and OkHttp
     */
    fun getSocialResponse() {
        socialResponseLMld.postValue(Response.loading(null, null))
        compositeDisposable.add(
            mainRepository.getSocial()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ social ->
                    socialResponseLMld.postValue(Response.success(social))
                }, {
                    socialResponseLMld.postValue(Response.error(null, null))
                })
        )
    }

    /** Get ProductResponse Using Hilt Rxjava Retrofit and OkHttp
     */
    fun getProductResponse() {
        productResponseLMld.postValue(Response.loading(null, null))
        compositeDisposable.add(
            mainRepository.getProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ product ->
                    productResponseLMld.postValue(Response.success(product))
                    imageUrl.value = product.image!!
                    productName.value = product.name!!

                }, {
                    productResponseLMld.postValue(Response.error(null, null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        //clear disposable
        compositeDisposable.dispose()
    }


}