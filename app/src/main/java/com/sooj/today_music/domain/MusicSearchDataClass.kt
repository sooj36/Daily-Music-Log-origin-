package com.sooj.today_music.domain

import com.google.gson.annotations.SerializedName

data class MusicSearchDataClass(
    @SerializedName("results") val results : Results
)

data class Results(
    @SerializedName("trackmatches") val trackmatches : Trackmatches
)

data class Trackmatches(
    @SerializedName("track") val track: List<Track>
)

data class Track (
    @SerializedName("name") val name : String,
    @SerializedName("artist") val artist : String,
    @SerializedName("image") val image: List<Image> // 이미지가 리스트로 반환
)

data class Image(
    @SerializedName("#text") val url : String,
    val size : String,
)