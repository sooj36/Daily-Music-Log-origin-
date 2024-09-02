package com.sooj.today_music.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TrackEntity::class], version = 1)
abstract class Database : RoomDatabase() {

    companion object {
        const val NAME = "name_DB"
    }

    abstract fun getDao() : Dao // db에 접근 가능한 다오 설정


}