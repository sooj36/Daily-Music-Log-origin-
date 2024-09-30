package com.sooj.today_music.domain

import com.sooj.today_music.room.MemoDao
import com.sooj.today_music.room.MemoEntity
import com.sooj.today_music.room.TrackDao
import com.sooj.today_music.room.TrackEntity
import java.util.concurrent.Flow

interface MemoRepository  {
    suspend fun saveMemo_impl(memoEntity: MemoEntity)

    suspend fun getMemo_impl(trackId : Int): kotlinx.coroutines.flow.Flow<MemoEntity>

    suspend fun editMemo_impl(memoEntity: MemoEntity)

    suspend fun deleteMemo_impl(id : Int)


}