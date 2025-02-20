package com.sooj.today_music.domain

import com.sooj.today_music.BuildConfig
import com.sooj.today_music.room.MemoEntity
import com.sooj.today_music.room.TrackEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SearchRepository {
    suspend fun getMusic_impl(track: String): List<Track>

    suspend fun getAlbumPoster_impl(track: String, artist : String) : Album?

    suspend fun saveSelectedTrack_impl(trackEntity: TrackEntity, memoEntity: MemoEntity)

    fun getAllTracks_impl() : Flow<List<TrackEntity>>
    // suspend 한번만 값을 반환

    suspend fun deleteTrack_impl(trackEntity: TrackEntity)

    suspend fun getPostInfo_impl(track: String): List<Album>


}


