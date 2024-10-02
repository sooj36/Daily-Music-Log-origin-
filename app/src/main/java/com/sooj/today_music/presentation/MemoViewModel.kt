package com.sooj.today_music.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooj.today_music.domain.MemoRepository
import com.sooj.today_music.room.MemoDao
import com.sooj.today_music.room.MemoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val memoRepository: MemoRepository,
    private val memoDao: MemoDao
) : ViewModel() {

    // 각 trackID별로 메모 리스트 관리 (MAP)
    private val _memoMap = mutableStateOf<Map<Int, List<MemoEntity>>>(emptyMap())
    val memoMap: State<Map<Int, List<MemoEntity>>> get() = _memoMap


    // 특정 트랙의 메모를 Flow로 가져오기
//    fun loadMemoForTrack(trackId: Int) {
//        viewModelScope.launch {
//            memoRepository.getMemo_impl(trackId).collect() { memoEntity ->
//                _memoContent.value = memoEntity // stateflow 업데이트
//            }
//        }
//    }



    // 메모 DB 저장g
    fun saveMemo_vm(trackId: Int, memoContent: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val memoEntity = MemoEntity(
                    trackId = trackId, memoContent = memoContent
                )
//                memoDao.insertMemo(memoEntity) // 이건 impl에서 하는 일
                // 메모 저장
                memoRepository.saveMemo_impl(memoEntity)


            } catch (e: Exception) {
                Log.e("saveMemo_vm", "Error saving memo: ${e.message}")
            }
        }
    }

    // trackId 에 해당하는 메모 데이터 로드

}