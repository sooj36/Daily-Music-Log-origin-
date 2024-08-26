package com.sooj.today_music.data


import android.util.Log
import com.sooj.today_music.BuildConfig
import com.sooj.today_music.domain.MusicModel_dc
import com.sooj.today_music.domain.Results
import com.sooj.today_music.domain.SearchRepository
import com.sooj.today_music.domain.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


class SearchRepositoryImpl @Inject constructor(
    private val musicapi: ApiService_EndPoint
) : SearchRepository {
    override suspend fun getTrackInfo(track: String): List<Track> {
        val response = musicapi.getMusicSearch(
            "track.search", "", BuildConfig.LAST_FM_API_KEY, "json"
        )

        if (response.isSuccessful) {
            // 응답 성공 시,
            val track = response.body()?.results?.trackmatches?.track
            return track ?: emptyList()
        } else {
            // 에러 처리
            Log.e("ERROR", "ERROR CODE = ${response.code()}")
            return emptyList()
        }
    }
}