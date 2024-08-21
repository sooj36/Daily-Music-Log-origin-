package com.sooj.today_music.data


import com.sooj.today_music.domain.MusicSearchDataClass
import retrofit2.http.GET
import retrofit2.http.Query
// 인터페이스를 구현한 Retrofit API 인스턴스
interface ApiService {
    @GET("2.0/")
    suspend fun getMusicSearch() : List<MusicSearchDataClass>
}

interface ApiService2 {
    @GET("2.0/")
    suspend fun getMusicSearch(
        @Query("method") method: String = "track.search",
        @Query("track") trackName: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String = "json"
    ): MusicSearchDataClass
}


// /2.0/?method=track.search&track=Believe&api_key=YOUR_API_KEY&format=json