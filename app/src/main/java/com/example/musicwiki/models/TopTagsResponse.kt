package com.example.musicwiki.models

import com.google.gson.annotations.SerializedName

data class TopTagsResponse(
    @SerializedName("toptags")
    val topTags: TopTags
)
