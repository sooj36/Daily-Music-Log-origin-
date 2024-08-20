package com.sooj.today_music.data


import com.sooj.today_music.domain.MusicSearchDataClass
import retrofit2.http.GET

interface ApiService {
    @GET("music_search")
    suspend fun getMusicSearch() : List<MusicSearchDataClass>
}