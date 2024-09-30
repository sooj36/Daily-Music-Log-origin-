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

    suspend fun saveTrackWithMemo(trackEntity: TrackEntity, trackDao: TrackDao, memoDao: MemoDao) {
        // 트랙 저장 후 trackId 반환
        val trackId = trackDao.insertData(trackEntity).toInt()

        // 트랙 저장 후 해당 트랙에 대한 기본 메모 생성
        val memoEntity = MemoEntity(trackId = trackId, memoContent = "오늘의 음악을 기록하세 impl")
        memoDao.insertMemo(memoEntity)
    }
}