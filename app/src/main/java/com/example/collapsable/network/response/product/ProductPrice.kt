package com.example.collapsable.network.response.product

import com.google.gson.annotations.SerializedName


class ProductPrice(
    @field:SerializedName("value")
    val value: Float? = 0f,
    @field:SerializedName("currency")
    val currency: String? = ""
)