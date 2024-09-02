package com.sooj.today_music.domain

import com.sooj.today_music.BuildConfig
import com.sooj.today_music.room.TrackEntity
import retrofit2.Response

interface SearchRepository {
    suspend fun getTrackInfo(track: String): List<Track>

    suspend fun getPostInfo(track: String, artist : String) : Album?

    suspend fun saveToTrack(trackEntity: TrackEntity)

    suspend fun getAllTracks() : List<TrackEntity>

}


