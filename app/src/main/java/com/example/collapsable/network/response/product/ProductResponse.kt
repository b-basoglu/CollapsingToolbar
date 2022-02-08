package com.example.collapsable.network.response.product

import com.google.gson.annotations.SerializedName

class ProductResponse(
    @field:SerializedName("id")
    val id: Int? = 0,
    @field:SerializedName("name")
    val name: String? = "",
    @field:SerializedName("desc")
    val desc: String? = "",
    @field:SerializedName("image")
    val image: String? = "",
    @field:SerializedName("price")
    val price: ProductPrice?
)