package com.example.collapsable.network.response.social

import com.google.gson.annotations.SerializedName

class SocialResponse(
    @field:SerializedName("likeCount")
    val likeCount: Int? = 0,
    @field:SerializedName("commentCounts")
    val commentCounts: SocialCommentCounts?
)