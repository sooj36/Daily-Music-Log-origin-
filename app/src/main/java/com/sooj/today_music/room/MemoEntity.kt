package com.sooj.today_music.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MemoEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val memo : String,
)
