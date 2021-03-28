package com.rajkumarrajan.mvvm_architecture.data.api

import com.example.dolaptendyol.network.response.Product.ProductResponse
import com.example.dolaptendyol.network.response.Social.SocialResponse
import io.reactivex.Single

interface ApiHelper {
    fun getProduct(): Single<ProductResponse>
    fun getSocial(): Single<SocialResponse>
}