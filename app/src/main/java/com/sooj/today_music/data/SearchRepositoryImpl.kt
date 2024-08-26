package com.sooj.today_music.data


import com.sooj.today_music.domain.MusicModel_dc
import com.sooj.today_music.domain.SearchRepository
import javax.inject.Inject


class SearchRepositoryImpl @Inject constructor(
    private val api: ApiService_EndPoint
) : SearchRepository {
    override suspend fun getTrackInfo(track: String): MusicModel_dc {
        TODO("Not yet implemented")
    }

}