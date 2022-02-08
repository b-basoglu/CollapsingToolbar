package com.example.collapsable.network.api

import com.example.collapsable.network.constants.NetworkConstants
import com.example.collapsable.network.response.product.ProductResponse
import com.example.collapsable.network.response.social.SocialResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
    @GET(NetworkConstants.PRODUCT_URL)
    fun getProduct(): Single<ProductResponse>

    @GET(NetworkConstants.SOCIAL_URL)
    fun getSocial(): Single<SocialResponse>
}