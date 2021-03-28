package com.example.dolaptendyol.network.repository

import com.example.dolaptendyol.network.response.Product.ProductResponse
import com.example.dolaptendyol.network.response.Social.SocialResponse
import com.rajkumarrajan.mvvm_architecture.data.api.ApiHelper
import io.reactivex.Single
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    fun getProduct(): Single<ProductResponse> {
        return apiHelper.getProduct()
    }

    fun getSocial(): Single<SocialResponse> {
        return apiHelper.getSocial()
    }

}