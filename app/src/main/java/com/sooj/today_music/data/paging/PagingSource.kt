package com.sooj.today_music.data.paging

import com.sooj.today_music.room.TrackDao
import javax.inject.Inject

class PagingSource @Inject constructor(
    private val trackDao: TrackDao,
)
{
}