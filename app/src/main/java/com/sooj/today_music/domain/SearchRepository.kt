package com.sooj.today_music.domain

import com.sooj.today_music.BuildConfig
import com.sooj.today_music.room.MemoEntity
import com.sooj.today_music.room.TrackEntity
import retrofit2.Response

interface SearchRepository {
    suspend fun getMusic_impl(track: String): List<Track>

    suspend fun getAlbumPoster_impl(track: String, artist : String) : Album?

    suspend fun saveSelectedTrack_impl(trackEntity: TrackEntity, memoEntity: MemoEntity)

    suspend fun getAllTracks_impl() : List<TrackEntity>

    suspend fun deleteTrack_impl(trackEntity: TrackEntity)

//    suspend fun getPostInfo(track: String): Album

}


