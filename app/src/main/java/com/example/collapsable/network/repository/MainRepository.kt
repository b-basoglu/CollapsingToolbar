package com.example.collapsable.network.repository

import com.example.collapsable.network.response.product.ProductResponse
import com.example.collapsable.network.response.social.SocialResponse
import com.example.collapsable.network.api.ApiHelper
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