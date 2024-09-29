package com.sooj.today_music.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {

//    @Query("SELECT * FROM MemoEntity WHERE trackId = :trackId")
//    fun getMemoByTrackId(trackId: Int): LiveData<MemoEntity> // trackId로 메모 조회
//    /** suspend fun 과 LiveData 같이 사용 불가*/

    @Query("SELECT * FROM memo_table WHERE trackId = :trackId")
    fun getMemoByTrackId(trackId: Int): Flow<MemoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memoEntity: MemoEntity)

    @Update
    suspend fun updateMemo(memoEntity: MemoEntity)

    @Query("delete FROM memo_table where trackId = :id")
    suspend fun deleteMemo(id: Int)

}

@Dao
interface TrackDao {

    @Query("SELECT * FROM track_table") // 모든 트랙 조회
    suspend fun getAllData(): List<TrackEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(trackEntity: TrackEntity): Long

    @Delete
    suspend fun deleteData(trackEntity: TrackEntity)

}