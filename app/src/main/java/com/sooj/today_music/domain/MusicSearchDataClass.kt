package com.sooj.today_music.domain

data class MusicSearchDataClass(
    val track : Track
)


data class Track (
    val name : String,
    val artist : String,
    val image: Image
)