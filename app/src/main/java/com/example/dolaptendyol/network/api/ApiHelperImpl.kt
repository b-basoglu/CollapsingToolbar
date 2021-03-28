package com.rajkumarrajan.mvvm_architecture.data.api

import com.example.dolaptendyol.network.response.Product.ProductResponse
import com.example.dolaptendyol.network.response.Social.SocialResponse
import io.reactivex.Single
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) :
    ApiHelper {
    override fun getProduct(): Single<ProductResponse> {
        return apiService.getProduct()
    }

    override fun getSocial(): Single<SocialResponse> {
        return apiService.getSocial()
    }
}