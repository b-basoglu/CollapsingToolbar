package com.rajkumarrajan.mvvm_architecture.data.api

import com.example.dolaptendyol.network.constants.NetworkConstants
import com.example.dolaptendyol.network.response.Product.ProductResponse
import com.example.dolaptendyol.network.response.Social.SocialResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
    @GET(NetworkConstants.PRODUCT_URL)
    fun getProduct(): Single<ProductResponse>

    @GET(NetworkConstants.SOCIAL_URL)
    fun getSocial(): Single<SocialResponse>
}