package com.sooj.today_music.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooj.today_music.domain.Image2
import com.sooj.today_music.domain.MemoRepository
import com.sooj.today_music.domain.SearchRepository
import com.sooj.today_music.domain.Track
import com.sooj.today_music.room.MemoEntity
import com.sooj.today_music.room.TrackEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val repository: SearchRepository,
    private val memoRepository: MemoRepository
    /** viewmodel 생성 시 Hilt가 알아서 repo 제공해주고, 이 주입받은 repo통해 데이터 처리*/
) : ViewModel() {
    // 로드 시작 시간 기록용
    private val _startTime = MutableStateFlow(0L)
    val startTime : StateFlow<Long> get() = _startTime.asStateFlow()

    // 검색 stateflow
    private val _searchList_st = MutableStateFlow<List<Track>>(emptyList()) // 여러 개의 객체 담고 있어서 List
    val searchList_st: StateFlow<List<Track>> get() = _searchList_st.asStateFlow()


    // 선택 -> stateflow
    private val _selectedTrack_st = MutableStateFlow<Track?>(null)
    val selectedTrack_st: StateFlow<Track?> get() = _selectedTrack_st.asStateFlow()

//    /** 선택 트랙에서 Artist, Track명으로 get.Info 가져오기 -> StateFlow 로 변경 */
//    private val _getAlbumImage_st = MutableStateFlow<String?>(null)
//    val getAlbumImage_st: StateFlow<String?> get() = _getAlbumImage_st.asStateFlow()

    /** 선택 트랙에서 Artist, Track명으로 get.Info 가져오기 -> StateFlow 로 변경 */
    private val _getAlbumMap_st = MutableStateFlow<Map<String,String?>>(emptyMap())
    val getAlbumMap_st: StateFlow<Map<String,String?>> get() = _getAlbumMap_st.asStateFlow()

    /** 모든 트랙 데이터 상태 관리 */ // StateFlow 로 변경 */
    private val _getAllSavedTracks_st = MutableStateFlow<List<TrackEntity>>(emptyList())
    val getAllSavedTracks_st: StateFlow<List<TrackEntity>> = _getAllSavedTracks_st.asStateFlow()

    private val _selectedTrackEntity_st = MutableStateFlow<TrackEntity?>(null)
    val selectedTrackEntity_st: StateFlow<TrackEntity?> get() = _selectedTrackEntity_st.asStateFlow()

    // 선택
    private val _memoContent_st = MutableStateFlow<MemoEntity?>(null)
    val memoContent_st: StateFlow<MemoEntity?> get() = _memoContent_st.asStateFlow()

    // db저장 성공 or 실패
    private var _saveResult_st = MutableStateFlow<Boolean?>(null)
    val saveResult_st : StateFlow<Boolean?> get() = _saveResult_st.asStateFlow()

    /** track을 기반으로 음악 정보를 검색하고, 그 결과를 viewmodel 상태로 저장 */
    fun getMusic_vm(track: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("sj_vm(st) GETMUSIC", "Running on thread: ${Thread.currentThread().name}")
            try {
                val trackInfo = repository.getMusic_impl(track)
                withContext(Dispatchers.Main) {
                    _searchList_st.value = trackInfo

                }
            } catch (e: Exception) {
                Log.e("sj VIEWMODEL ERROR!!", "ERROR FETCHING TRACK INFO ${e.message}")
            }
            Log.d("sj_vm(en) GETMUSIC", "Running on thread: ${Thread.currentThread().name}")
        }
    }

    // 선택한 트랙
    fun selectTrack_vm(track: Track) {
        Log.d("SELECT_TRACK", "Selected Track - Name: ${track.name}, Artist: ${track.artist}")
        _selectedTrack_st.value = track
        // 추가 로그: 선택된 트랙 상태 업데이트 후 상태 확인
        Log.d("SELECT_TRACK", "Updated _selectedTrack_st - ${_selectedTrack_st.value}")
//        getAlbumPoster_vm() // 앨범 포스터 불러오기

    }

    fun selectTrackEntity_vm(trackEntity: TrackEntity) {
        //imgurl 사용하여 image2 객체 생성 후 리스트로 변환
        val imgList = listOf(
            Image2(url = trackEntity.imageUrl, size = "extralarge")
        )

        // trackentity -> track 변환
        val convertTrack = Track(
            name = trackEntity.trackName,
            artist = trackEntity.artistName,
            image = imgList
        )
        // 선택된 trackentity데이터를 selecttrack으로 업데이트
        _selectedTrack_st.value = convertTrack
        Log.d("select Track", "Selected track from entity updated: ${_selectedTrack_st.value}")
    }

    // 1006 추가 로직 URL -> MAP으로
    fun fetchTrackAndUrl_vm(track : String) {
        _startTime.value = System.currentTimeMillis()

        viewModelScope.launch(Dispatchers.IO) {
            val trackINFO = repository.getMusic_impl(track)

            // 트랙 이름 기반으로 api 2 호출하여 이미지 가져오기
            val albumImg : Map<String, String?> = trackINFO.associate { url ->
                val posterUrl = repository.getAlbumPoster_impl(
                    url.name ?: "fail to load track",
                    url.artist ?: "fail to load artist"
                )
                // 트랙명 key, url value
                (url.name ?: "track_key") to (posterUrl?.image?.find { it.size == "extralarge" }?.url)
            }

            // 기존 map에 새로운 값 추가
            withContext(Dispatchers.Main) {
                _getAlbumMap_st.value = _getAlbumMap_st.value + albumImg
            }
        }
    }

    // Dao에 저장된 데이터 불러오는 메서드
    fun getAllTracks_vm() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _getAllSavedTracks_st.value = repository.getAllTracks_impl()
                Log.d("sj--call all data", "tracks load ${_getAllSavedTracks_st.value.size} gut ")
            } catch (e: Exception) {
                Log.e("sj--call data_error", "트랙s 로드 ${e.message} 오류")
            }
            Log.d("sj vm GETALL", "Running on thread: ${Thread.currentThread().name}")
        }
    }

    // 선택된 트랙을 데이터베이스에 저장
    /////////////////////////////////////////수정할 부분
    ////////////선택된 트랙을 데이터베이스에 저장하는 건 맞지만
    /////////이미지부분 변경해야함 //////////////////////////
    fun saveSelectedTrack_vm() {
        val trackToSave = _selectedTrack_st.value
        val urlToSave = _getAlbumMap_st.value
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val trackEntity = urlToSave[trackToSave?.name]?.let {
                    TrackEntity(
                        trackName = trackToSave?.name ?: return@launch,
                        artistName = trackToSave.artist ?: return@launch,
                        imageUrl = it, //
                        saveAt = System.currentTimeMillis()
                    )
                }
                val memoEntity = trackEntity?.trackId?.let {
                    MemoEntity(
                        trackId = it,
                        memoContent = ""
                    )
                }
                if (trackEntity != null) {
                    if (memoEntity != null) {
                        repository.saveSelectedTrack_impl(trackEntity, memoEntity)
                    }
                } // db저장 코드
                Log.d("sj--db save", "track is ${trackEntity} gut")
                _saveResult_st.value = true
            } catch (e: Exception) {
                Log.e("sj--db error", "track is ${e.message} error")
                _saveResult_st.value = false
            }
        }
    }

    // 트랙 선택 시 trackId으로 memoentity 불러오기
    fun getMmUseID_vm(trackId: Int) {
        Log.d("mem", "getMmUseID_vm called with trackId: $trackId")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                memoRepository.getMemo_impl(trackId).collect() { mm ->
                    Log.d("mem", "Fetched MemoEntity: $mm")
                    _memoContent_st.value = mm
                    Log.d("mem", "_memoContent_st updated: ${_memoContent_st.value}")
                }
            } catch (e: Exception) {
                Log.e("mem", "${e.message}")
            }
        }
    }

    fun loadTrackID_vm(trackEntity: TrackEntity) {
        _selectedTrackEntity_st.value = trackEntity
        getMmUseID_vm(trackEntity.trackId) // 자동 생성된 trackId로 MemoEntity 조회
    }

    // 검색 페이지 검색 값 초기화
    fun clearSearchResults() {
        _searchList_st.value = emptyList()  // 검색 결과 초기화
    }

    fun clearMap() {
        _getAlbumMap_st.value = emptyMap()
    }

    // 트랙 삭제
    fun deleteSavedTrack(trackEntity: TrackEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteTrack_impl(trackEntity)

            } catch (e : Exception) {
                Log.e("delete_tr", "${e.message}")
            }
        }
    }
}


// 1. @HiltViewModel을 통해 Hilt를 사용하여 의존성 주입을 적용
// 2. viewModelScope.launch 사용하여 비동기 작업 처리
// 3. StateFlow 사용하여 반응형 데이터 스트림 구현
// 4. Repo 패턴 : 주입받아 데이터 처리 위임
// 5. Room Database : 두 Entity 사용해 로컬 데이터 베이스 작업
// 6. API 호출
// 7. 상태 관리 : mutableStateOf 와 mutableStateFlow 사용해 UI 상태 관리
// 8. 멀티 스레딩 : 디스패처 .IO / .Main 사용