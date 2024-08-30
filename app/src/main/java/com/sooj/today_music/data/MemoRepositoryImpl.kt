package com.sooj.today_music.data

import android.content.SharedPreferences
import com.sooj.today_music.domain.MemoRepository

import javax.inject.Inject


class MemoRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : MemoRepository {

    companion object {
        private const val MEMO_KEY = "memo_key"
    }

    override suspend fun saveMemo(content: String) {
        sharedPreferences.edit().putString(MEMO_KEY, content).apply()
    }

    override suspend fun getMemo(): String {
        return sharedPreferences.getString(MEMO_KEY, "") ?: ""
    }
}

/// 메모데이터 자장 및 불러오기 기능