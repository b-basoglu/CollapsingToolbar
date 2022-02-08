package com.example.collapsable.network.api

import com.example.collapsable.network.response.product.ProductResponse
import com.example.collapsable.network.response.social.SocialResponse
import io.reactivex.Single

interface ApiHelper {
    fun getProduct(): Single<ProductResponse>
    fun getSocial(): Single<SocialResponse>
}