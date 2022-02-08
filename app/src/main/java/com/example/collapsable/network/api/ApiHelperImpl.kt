package com.example.collapsable.network.api

import com.example.collapsable.network.response.product.ProductResponse
import com.example.collapsable.network.response.social.SocialResponse
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