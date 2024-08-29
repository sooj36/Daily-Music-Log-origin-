package com.sooj.today_music.domain

import com.sooj.today_music.BuildConfig
import retrofit2.Response

interface SearchRepository {
    suspend fun getTrackInfo(track: String): List<Track>

    suspend fun getPostInfo(track: String) : List<Track2>

}


