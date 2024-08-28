package com.sooj.today_music.data


import android.util.Log
import com.sooj.today_music.BuildConfig
import com.sooj.today_music.domain.Album
import com.sooj.today_music.domain.SearchRepository
import com.sooj.today_music.domain.Track
import com.sooj.today_music.domain.Track2
import javax.inject.Inject


class SearchRepositoryImpl @Inject constructor(
    private val musicapi: ApiService_EndPoint
) : SearchRepository {
    override suspend fun getTrackInfo(track: String): List<Track> {
        val searchResponse = musicapi.getTrackSearch(
            "track.search", track, BuildConfig.LAST_FM_API_KEY, "json"
        )

        if (searchResponse.isSuccessful) {
            // 응답 성공 시,
            val searchModel = searchResponse.body()
            Log.d("API RESPONSE", "MusicModel: ${searchModel?.results}")

            // 응답 성공 시,
            val searchList = searchResponse.body()?.results?.trackmatches?.track
            Log.d("RESPONSE SUCCES", "SUCCESS ${searchResponse.body()}")
            Log.d("RESPONSE Track value", "SUCCESS ${searchResponse.code()}")
            return searchList ?: emptyList()

        } else {
            // 에러 처리
            Log.e("ERROR", "ERROR CODE = ${searchResponse.code()}")
            return emptyList()
        }
    }


    override suspend fun getPostInfo(track: String): List<Album> {
        val searchRespose = musicapi.getTrackSearch(
            "track.search", track, BuildConfig.LAST_FM_API_KEY, "json"
        )

        if (searchRespose.isSuccessful) {
            val searchList = searchRespose.body()?.results?.trackmatches?.track ?: emptyList()

            // 각 트랙의 세부정보를 가져오기 위해 추가적인 API 호출 //
            val getAlbumPost = searchList.mapNotNull { trackItem ->
                val artistName = trackItem.artist ?: return@mapNotNull null
                val trackName = trackItem.name ?: return@mapNotNull null

                // getPostInfo API 이용해서 이미지 정보 가져오기 //
                val postResponse = musicapi.getPostInfo(
                    "track.getInfo", BuildConfig.LAST_FM_API_KEY, artistName, trackName, "json"
                )

                if (postResponse.isSuccessful) {
                    postResponse.body()?.track?.album
                } else {
                    Log.e("PostInfo 에러", "PostInfo 에러 코드는 ${postResponse.code()}")
                    null
                }
            } // map not null
        } else {
            Log.e("", "")
        }
        return emptyList() // 수정해야함요
    }
}