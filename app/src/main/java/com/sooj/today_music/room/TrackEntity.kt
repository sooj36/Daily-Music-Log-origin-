package com.sooj.today_music.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val track : String,
    val artist : String,
)

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = TrackEntity::class,
            parentColumns = ["id"],
            childColumns = ["trackId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class MemoEntity(
    @PrimaryKey val trackId : Int,
    val id : Int = 0,
    val memo : String,
)
