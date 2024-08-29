package com.sooj.today_music.data

import android.content.SharedPreferences
import com.sooj.today_music.domain.MemoRepository
import javax.inject.Inject


class MemoRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : MemoRepository {
    override suspend fun saveMemo(memo: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getMemo(): String {
        TODO("Not yet implemented")
        return ""
    }
}