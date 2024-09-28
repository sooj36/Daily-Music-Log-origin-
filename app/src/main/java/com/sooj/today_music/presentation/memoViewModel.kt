package com.sooj.today_music.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooj.today_music.domain.MemoRepository
import com.sooj.today_music.room.MemoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class memoViewModel @Inject constructor(
    private val memoRepository: MemoRepository
) : ViewModel() {

    // 각 trackID별로 메모 리스트 관리 (MAP)
    private val _memoMap = mutableStateOf<Map<Int, List<MemoEntity>>>(emptyMap())
    val memoMap : State<Map<Int, List<MemoEntity>>> get() = _memoMap

    // 선택
    private val _selectMemo = mutableStateOf<List<MemoEntity>>(emptyList())
    val selectMemo : State<List<MemoEntity>> get() = _selectMemo

    // 트랙에 맞는 메모 가져오기
    fun  getMemo(trackId: Int) : List<MemoEntity> {
        return _memoMap.value[trackId] ?: emptyList()
    }

    // 메모 DB 저장
    fun saveMemo_vm(trackId : Int, memoText : String) {
        val memoToSave = _memoMap.value ?: return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val memoEntity = MemoEntity(
                    trackId = trackId, // 전달받은 트랙 id사용
                    memo = memoText // 전달받은 메모 내용을 사용
                )
                // 메모 저장
                memoRepository.saveMemo_impl(memoEntity)

                // 저장된 메모를 trackid에 맞는 리스트에 추가
               val currentMemoList = getMemo(trackId) + memoEntity
                _memoMap.value = _memoMap.value.toMutableMap().apply {
                    put(trackId, currentMemoList)
                }

            } catch (e : Exception) {
                Log.e("saveMemo_vm", "Error saving memo: ${e.message}")
            }
        }
    }
}