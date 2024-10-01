package com.sooj.today_music.data


import com.sooj.today_music.BuildConfig
import com.sooj.today_music.domain.MusicGetInfoModel_dc
import com.sooj.today_music.domain.MusicSearchModel_dc
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
// 인터페이스를 구현한 Retrofit API 인스턴스


interface ApiService_EndPoint {
    @GET("2.0/")
    suspend fun getTrackSearch(
        @Query("method") method: String = "track.search",
        @Query("track") track: String = "",
        @Query("api_key") apiKey :String = BuildConfig.LAST_FM_API_KEY,
        @Query("format") format: String = "json",
        @Query("limit") limit : Int = 7
    ): Response<MusicSearchModel_dc>

    @GET("2.0/")
    suspend fun getPostInfo(
        @Query("method") method: String = "track.getInfo",
        @Query("api_key") apiKey: String = BuildConfig.LAST_FM_API_KEY,
        @Query("artist") artist : String,
        @Query("track") track: String = "",
        @Query("format") format: String = "json"
    ) : Response<MusicGetInfoModel_dc>
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

/** /2.0/
 * ?
 * method=track.getInfo
 * &
 * api_key=
 * &
 * artist="cher"
 * &
 * track="believe"
 * &
 * format="json"*/