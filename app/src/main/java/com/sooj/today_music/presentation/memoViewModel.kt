package com.sooj.today_music.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sooj.today_music.domain.MemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//@HiltViewModel
//class memoViewModel @Inject constructor(
//    private val memoRepository: MemoRepository
//) : ViewModel() {
//
//    private val _memoContent = mutableStateOf("")
//    val memoContent: State<String> get() = _memoContent
//
//    suspend fun loadMemo() {
//        _memoContent.value = memoRepository.getMemo()
//    }
//
//    suspend fun saveMemo(content: String) {
//        memoRepository.saveMemo(content) //메모내용 저장
//        _memoContent.value = content // 내부상태 업데이트
//    }
//}