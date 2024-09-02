package com.sooj.today_music.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {

    @Query("SELECT * FROM MemoEntity")
    fun getAllData(): LiveData<List<TrackEntity>>

    @Insert
    fun addMemo(trackEntity: TrackEntity)

    @Query("delete FROM trackentity where id = :id")
    fun deleteMemo(id: Int) {
    }

}