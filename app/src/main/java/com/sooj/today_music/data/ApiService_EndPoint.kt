package com.sooj.today_music.data


import com.sooj.today_music.domain.MusicModel_dc
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
// 인터페이스를 구현한 Retrofit API 인스턴스
interface ApiService_EndPoint {
    @GET("/2.0")
    suspend fun getMusicSearch(
        @Query("method") method: String = "track.search",
        @Query("track") track: String = "",
        @Query("api_key") api_key: String,
        @Query("format") format: String = "json",
    ): Response<MusicModel_dc>
}


/** /2.0/
 * ?
 * method=track.search
 * &
 * track=Believe
 * &
 * api_key=YOUR_API_KEY
 * &
 * format=json */