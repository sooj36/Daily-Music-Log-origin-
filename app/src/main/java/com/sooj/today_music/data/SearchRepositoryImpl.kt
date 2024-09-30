package com.sooj.today_music.data


import android.util.Log
import androidx.room.Transaction
import com.sooj.today_music.BuildConfig
import com.sooj.today_music.domain.Album
import com.sooj.today_music.domain.SearchRepository
import com.sooj.today_music.domain.Track
import com.sooj.today_music.room.MemoDao
import com.sooj.today_music.room.MemoEntity
import com.sooj.today_music.room.TrackDao
import com.sooj.today_music.room.TrackEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SearchRepositoryImpl @Inject constructor(
    private val musicapi: ApiService_EndPoint,
    private val trackDao: TrackDao , // 다오 주입받음
    private val memoDao: MemoDao
) : SearchRepository {
    override suspend fun getMusic_impl(track: String): List<Track> {
        return withContext(Dispatchers.IO) {
            Log.d("sj im GETMUSIC", "Running on thread: ${Thread.currentThread().name}")
            val searchResponse = musicapi.getTrackSearch(
                "track.search", track, BuildConfig.LAST_FM_API_KEY, "json"
            )

            if (searchResponse.isSuccessful) {
                // 응답 성공 시,
                val searchList = searchResponse.body()?.results?.trackmatches?.track
                Log.d(
                    "RESPONSE SUCCES",
                    "SUCCESS ${searchResponse.body()},${searchResponse.code()}"
                )
                searchList ?: emptyList()

            } else {
                // 에러 처리
                Log.e("ERROR", "ERROR CODE = ${searchResponse.code()}")
                emptyList()
            }
        }
    }

    override suspend fun getAlbumPoster_impl(track: String, artist: String): Album? {
        return withContext(Dispatchers.IO) {
            Log.d("sj im GETPOSTER", "Running on thread: ${Thread.currentThread().name}")
            try {
                //getpostinfo api 호출
                val postResponse = musicapi.getPostInfo(
                    "track.getInfo", BuildConfig.LAST_FM_API_KEY, artist, track, "json"
                )
                if (postResponse.isSuccessful) {
                    val album = postResponse.body()?.track?.album
                    Log.d("post response gut ", "gut < ${album} >")
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

    @Transaction
    override suspend fun saveSelectedTrack_impl(trackEntity: TrackEntity, memoEntity: MemoEntity) {
        withContext(Dispatchers.IO) {
            Log.d("sj im(st) SAVE", "Running on thread: ${Thread.currentThread().name}")
            trackDao.insertData(trackEntity)
            val trackId = trackDao.insertData(trackEntity).toInt()

            val memoId = memoEntity.copy(trackId = trackId)
            memoDao.insertMemo(memoId)

        }
        Log.d("sj im(en) SAVE", "Running on thread: ${Thread.currentThread().name}")
    }

    override suspend fun getAllTracks_impl(): List<TrackEntity> {
        Log.d("sj im GETALL", "Running on thread: ${Thread.currentThread().name}")
        return withContext(Dispatchers.IO) {
            trackDao.getAllData()
        }
        Log.d("sj im(beh) GETALL", "Running on thread: ${Thread.currentThread().name}")
    }

    override suspend fun deleteTrack_impl(trackEntity: TrackEntity) {
        return withContext(Dispatchers.IO) {

            trackDao.deleteData(trackEntity)
        }
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
//        } else {
//            Log.e("", "")
//
//        }
//    }
}