package com.sooj.today_music.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "track_table")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int =0,
    val trackName : String?,
    val artistName : String?,
    val imageUrl : String?,
)

@Entity(
    tableName = "memo_table",
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
    val memo : String,
)
