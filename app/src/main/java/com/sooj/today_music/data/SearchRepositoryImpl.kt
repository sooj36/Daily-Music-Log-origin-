package com.sooj.today_music.data


import android.util.Log
import com.sooj.today_music.BuildConfig
import com.sooj.today_music.domain.SearchRepository
import com.sooj.today_music.domain.Track
import javax.inject.Inject


class SearchRepositoryImpl @Inject constructor(
    private val musicapi: ApiService_EndPoint
) : SearchRepository {
    override suspend fun getTrackInfo(track: String): List<Track> {
        val searchResponse = musicapi.getMusicSearch(
            "track.search", track, BuildConfig.LAST_FM_API_KEY, "json"
        )

        if (searchResponse.isSuccessful) {
            // 응답 성공 시,
            val musicModel = searchResponse.body()
            Log.d("API RESPONSE", "MusicModel: ${musicModel?.results}")

            // 응답 성공 시,
            val track = searchResponse.body()?.results?.trackmatches?.track
            Log.d("RESPONSE SUCCES", "SUCCESS ${searchResponse.body()}")
            Log.d("RESPONSE Track value", "SUCCESS ${searchResponse.code()}")
            return track ?: emptyList()

        } else {
            // 에러 처리
            Log.e("ERROR", "ERROR CODE = ${searchResponse.code()}")
            return emptyList()
        }
    }
}

