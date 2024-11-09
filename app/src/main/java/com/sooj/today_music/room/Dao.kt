package com.sooj.today_music.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sooj.today_music.data.paging.PagingSource
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {

//    @Query("SELECT * FROM MemoEntity WHERE trackId = :trackId")
//    fun getMemoByTrackId(trackId: Int): LiveData<MemoEntity> // trackId로 메모 조회
//    /** suspend fun 과 LiveData 같이 사용 불가*/

    @Query("SELECT * FROM MemoEntity WHERE trackId = :trackId LIMIT 1")
    fun getMemoByTrackId(trackId: Int): Flow<MemoEntity>

    //@insert 도 entity전체를 넘겨줘야함
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memoEntity: MemoEntity)

    //@Update 함수의 문제는 파라미터로 엔티티 객체를 전달해야 한다는 점
    @Update
    suspend fun updateMemo(memoEntity: MemoEntity)

    @Query("delete FROM memoentity where trackId = :id")
    suspend fun deleteMemo(id: Int)

}

@Dao
interface TrackDao {

    @Query("SELECT * FROM trackentity") // 모든 트랙 조회
    suspend fun getAllData(): List<TrackEntity>

//    @Query("SELECT * FROM trackentity") // 모든 트랙 조회 -> 페이징 적용
//    suspend fun getAllDataPaged(): PagingSource

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(trackEntity: TrackEntity): Long

    @Delete
    suspend fun deleteData(trackEntity: TrackEntity)

}