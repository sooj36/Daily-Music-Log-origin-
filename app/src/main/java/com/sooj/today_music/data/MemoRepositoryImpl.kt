package com.sooj.today_music.data

import android.util.Log
import com.sooj.today_music.domain.MemoRepository
import com.sooj.today_music.room.MemoDao
import com.sooj.today_music.room.MemoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MemoRepositoryImpl @Inject constructor(
    // 다오 주입
    private val memoDao: MemoDao
) : MemoRepository {
    override suspend fun saveMemo_impl(memoEntity: MemoEntity) {
        withContext(Dispatchers.Main) {
            memoDao.insertMemo(memoEntity)
        }
        Log.d("sj insert_Memo", "Running on thread: ${Thread.currentThread().name}")
    }

    override suspend fun getMemo_impl(trackId : Int): Flow<MemoEntity> {
            return memoDao.getMemoByTrackId(trackId)
    }

    override suspend fun editMemo_impl(memoEntity: MemoEntity) {
        return withContext(Dispatchers.Main) {
            memoDao.updateMemo(memoEntity)
        }
    }

    override suspend fun deleteMemo_impl(trackid : Int) {
        return withContext(Dispatchers.Main) {
            memoDao.deleteMemo(trackid)
        }
    }
}