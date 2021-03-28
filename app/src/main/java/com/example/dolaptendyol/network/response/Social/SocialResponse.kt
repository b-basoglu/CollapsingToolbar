package com.example.dolaptendyol.network.response.Social

import com.google.gson.annotations.SerializedName

class SocialResponse(
    @field:SerializedName("likeCount")
    val likeCount: Int? = 0,
    @field:SerializedName("commentCounts")
    val commentCounts: SocialCommentCounts?
)