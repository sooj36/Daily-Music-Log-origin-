package com.sooj.today_music.domain

import com.google.gson.annotations.SerializedName

data class MusicSearchDataClass(
    @SerializedName("track") val track : Track
)


data class Track (
    @SerializedName("name") val name : String,
    @SerializedName("artist") val artist : String,
    @SerializedName("image") val image: String = ""
)