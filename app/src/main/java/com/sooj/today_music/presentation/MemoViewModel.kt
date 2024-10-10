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

    fun insertMemo_vm(memoEntity: MemoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                memoRepository.saveMemo_impl(memoEntity)
            } catch (e:Exception) {
                Log.e("insert m error", "${e.message}")
            }
        }
    }

    fun updateMemo_vm(memoEntity: MemoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                memoRepository.editMemo_impl(memoEntity)
            } catch (e : Exception) {
                Log.d("updatememo error", "${e.message}")
            }
        }
    }


    fun deleteMemo_vm(trackId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
              memoRepository.deleteMemo_impl(trackId)

            } catch (e: Exception) {
                Log.e("delete mm", "fail to mm delete ${e.message}")
            }
        }
    }
}