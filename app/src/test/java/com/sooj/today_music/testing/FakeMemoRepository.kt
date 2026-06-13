package com.sooj.today_music.testing

import com.sooj.today_music.domain.MemoRepository
import com.sooj.today_music.room.MemoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeMemoRepository : MemoRepository {
    private val memos = MutableStateFlow<Map<Int, MemoEntity>>(emptyMap())
    val inserted = mutableListOf<MemoEntity>()
    val updated = mutableListOf<MemoEntity>()
    val deletedIds = mutableListOf<Int>()

    fun seedMemo(memoEntity: MemoEntity) {
        memos.value = memos.value + (memoEntity.trackId to memoEntity)
    }

    override suspend fun saveMemo_impl(memoEntity: MemoEntity) {
        inserted += memoEntity
        memos.value = memos.value + (memoEntity.trackId to memoEntity)
    }

    override suspend fun getMemo_impl(trackId: Int): Flow<MemoEntity> {
        return memos.map { values ->
            values[trackId] ?: MemoEntity(trackId = trackId, memoContent = "")
        }
    }

    override suspend fun editMemo_impl(memoEntity: MemoEntity) {
        updated += memoEntity
        if (memos.value.containsKey(memoEntity.trackId)) {
            memos.value = memos.value + (memoEntity.trackId to memoEntity)
        }
    }

    override suspend fun deleteMemo_impl(id: Int) {
        deletedIds += id
        memos.value = memos.value - id
    }
}
