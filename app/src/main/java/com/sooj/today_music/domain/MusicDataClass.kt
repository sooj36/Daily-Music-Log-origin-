package com.sooj.today_music.domain

data class MusicDataClass(
    val artist : Artist
)

data class Artist (
    val name: String,
    val mbid : String,
    val url : String,
    val image : Image,
)

data class Image (
    val text : String,
    val size : String,
)
