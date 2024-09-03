package com.sooj.today_music.data


import android.util.Log
import com.sooj.today_music.BuildConfig
import com.sooj.today_music.domain.Album
import com.sooj.today_music.domain.SearchRepository
import com.sooj.today_music.domain.Track
import com.sooj.today_music.domain.Track2
import com.sooj.today_music.room.TrackDao
import com.sooj.today_music.room.TrackEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SearchRepositoryImpl @Inject constructor(
    private val musicapi: ApiService_EndPoint,
    private val trackDao: TrackDao  // 다오 주입받음
) : SearchRepository {
    override suspend fun getTrackInfo(track: String): List<Track> {
        return withContext(Dispatchers.IO) {
            val searchResponse = musicapi.getTrackSearch(
                "track.search", track, BuildConfig.LAST_FM_API_KEY, "json"
            )

            if (searchResponse.isSuccessful) {
                // 응답 성공 시,
                val searchList = searchResponse.body()?.results?.trackmatches?.track
                Log.d("RESPONSE SUCCES", "SUCCESS ${searchResponse.body()},${searchResponse.code()}")
                searchList ?: emptyList()

            } else {
                // 에러 처리
                Log.e("ERROR", "ERROR CODE = ${searchResponse.code()}")
                emptyList()
            }
        }
    }

    override suspend fun getPostInfo(track: String, artist: String): Album? {
        return withContext(Dispatchers.IO) {
            try {
                //getpostinfo api 호출
                val postResponse = musicapi.getPostInfo(
                    "track.getInfo", BuildConfig.LAST_FM_API_KEY, artist, track, "json"
                )
                if (postResponse.isSuccessful) {
                    val album = postResponse.body()?.track?.album
                    Log.d("포스트 응답 성공 ", "성공햇슈 ${album}")
                    album
                } else {
                    Log.e("error", "api call error")
                    null
                }
            } catch (e: Exception) {
                Log.e("PostInfo Error", "Exception: ${e.message}")
                null
            }
        }
    }

    override suspend fun saveToTrack(trackEntity: TrackEntity) {
        trackDao.insertTrack(trackEntity)
    }

    override suspend fun getAllTracks(): List<TrackEntity> {
        return trackDao.getAllTracks()
    }


//    override suspend fun getPostInfo(track: String): Album {
//        val searchResponse = musicapi.getTrackSearch(
//            "track.search", track, BuildConfig.LAST_FM_API_KEY, "json"
//        )
//
//        if (searchResponse.isSuccessful) {
//            val searchList = searchResponse.body()?.results?.trackmatches?.track ?: emptyList()
//
//            // 각 트랙의 세부정보를 가져오기 위해 추가적인 API 호출 //
//            val getAlbumPost = searchList.mapNotNull { trackItem ->
//                val artistName = trackItem.artist ?: return@mapNotNull null
//                val trackName = trackItem.name ?: return@mapNotNull null // 로드된 데이터들
//
//                // getPostInfo API 이용해서 이미지 정보 가져오기 //
//                val postResponse = musicapi.getPostInfo(
//                    "track.getInfo", BuildConfig.LAST_FM_API_KEY, artistName, trackName, "json"
//                )
//                Log.d("포스트 응답", "$${postResponse.body()}")
//
//                // post 응답 성공 시 //
//                if (postResponse.isSuccessful) {
//                    val postList = postResponse.body()?.track
//                    postList  // 이 부분이 Album 객체로 가정하고 반환합니다.
//                } else {
//                    Log.e("PostInfo 에러", "PostInfo 에러 코드는 ${postResponse.code()}")
//                    null
//                }
//            }.flatten() // 여러 개의 리스트를 하나로 평탄화
//            return
//
//        } else {
//            Log.e("", "")
//
//        }
    //        }
}