package com.example.collapsable.network.response.social

import com.google.gson.annotations.SerializedName

class SocialCommentCounts(
    @field:SerializedName("averageRating")
    val averageRating: Float? = 0f,
    @field:SerializedName("anonymousCommentsCount")
    val anonymousCommentsCount: Int? = 0,
    @field:SerializedName("memberCommentsCount")
    val memberCommentsCount: Int? = 0
)