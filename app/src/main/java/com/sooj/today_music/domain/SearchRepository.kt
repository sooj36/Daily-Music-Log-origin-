package com.sooj.today_music.domain

import com.sooj.today_music.BuildConfig
import com.sooj.today_music.data.RetrofitInstance_build
import retrofit2.Response

interface SearchRepository {
    suspend fun getTrackInfo(track: String): MusicModel_dc

}