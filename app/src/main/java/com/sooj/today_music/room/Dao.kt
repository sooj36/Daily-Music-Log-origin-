package com.sooj.today_music.room

import androidx.lifecycle.LiveData
import androidx.room.Dao

@Dao
interface Dao {

    fun getAllData() : LiveData<List<MemoEntity>>

    fun addMemo(memoEntity: MemoEntity)

    fun deleteMemo(id : Int) {}
}