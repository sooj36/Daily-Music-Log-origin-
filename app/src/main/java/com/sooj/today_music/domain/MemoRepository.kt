package com.sooj.today_music.domain

import com.sooj.today_music.room.MemoEntity

interface MemoRepository  {
    suspend fun saveMemo_impl(memoEntity: MemoEntity)

    suspend fun getMemo_impl(trackId : Int) : List<MemoEntity>

    suspend fun editMemo_impl(memoEntity: MemoEntity)

    suspend fun deleteMemo_impl(id : Int)
}