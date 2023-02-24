package com.example.musicwiki.models

import com.google.gson.annotations.SerializedName

data class TrackX(
    @SerializedName("@attr") val attr: AttrXXXXXX,
    val artist: ArtistXXX,
    val duration: Int,
    val name: String,
    val streamable: StreamableX,
    val url: String
)