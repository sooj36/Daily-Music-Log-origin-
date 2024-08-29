package com.sooj.today_music.domain

interface MemoRepository  {
    suspend fun saveMemo(memo : String)

    suspend fun getMemo() :String
}