package com.sooj.today_music.data

import com.sooj.today_music.domain.MusicSearchDataClass
import retrofit2.http.GET

interface MusicSearchApi {
    @GET("2.0")
    suspend fun getMusicSearch() : List<MusicSearchDataClass>
}