package com.sooj.today_music.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooj.today_music.domain.MemoRepository
import com.sooj.today_music.room.MemoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoViewModel : ViewModel {
    private val memoRepository: MemoRepository
    private val ioDispatcher: CoroutineDispatcher

    @Inject constructor(
        memoRepository: MemoRepository,
    ) : this(memoRepository, Dispatchers.IO)

    internal constructor(
        memoRepository: MemoRepository,
        ioDispatcher: CoroutineDispatcher
    ) : super() {
        this.memoRepository = memoRepository
        this.ioDispatcher = ioDispatcher
    }

    private val _memoListState = MutableStateFlow("")
    val memoListState : StateFlow<String> get() = _memoListState.asStateFlow()

    fun insertMemo_vm(memoEntity: MemoEntity) {
        viewModelScope.launch(ioDispatcher) {
            try {
                memoRepository.saveMemo_impl(memoEntity)
//                _memoListState.value = _memoListState.value + memoEntity

            } catch (e:Exception) {
                Log.e("insert m error", "${e.message}")
            }
        }
    }

    fun updateMemo_vm(memoEntity: MemoEntity) {
        viewModelScope.launch(ioDispatcher) {
            try {
                memoRepository.editMemo_impl(memoEntity)
            } catch (e : Exception) {
                Log.d("update m error", "${e.message}")
            }
        }
    }


    fun deleteMemo_vm(trackId:Int) {
        viewModelScope.launch(ioDispatcher) {
            try {
              memoRepository.deleteMemo_impl(trackId)

            } catch (e: Exception) {
                Log.e("delete mm error", "fail to mm delete ${e.message}")
            }
        }
    }
}
