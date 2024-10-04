package com.sooj.today_music.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooj.today_music.domain.MemoRepository
import com.sooj.today_music.room.MemoDao
import com.sooj.today_music.room.MemoEntity
import com.sooj.today_music.room.TrackEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val memoRepository: MemoRepository,
) : ViewModel() {

    // 각 trackID별로 메모 리스트 관리 (MAP)
    private val _memoMap = MutableStateFlow<MemoEntity?>(null)
    val memoMap: StateFlow<MemoEntity?> get() = _memoMap

    fun deleteMemo_vm(trackId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
              memoRepository.deleteMemo_impl(trackId)

            } catch (e: Exception) {
                Log.e("delete mm", "fail to mm delete ${e.message}")
            }
        }
    }

    // trackId 에 해당하는 메모 데이터 로드

}