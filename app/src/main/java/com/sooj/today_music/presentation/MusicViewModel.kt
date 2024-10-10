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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** _searchList.value <-- _searchList 값 가져와 외부에 노출
 * val searchList: State<List<Track>> get() = _searchList
get() 커스텀 게터 */

/** private val _infoList = mutableStateOf<List<Album>>(emptyList())
val infoList: State<List<Album>> get() = _infoList */

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val repository: SearchRepository,
    private val memoRepository: MemoRepository
    /** viewmodel 생성 시 Hilt가 알아서 repo 제공해주고, 이 주입받은 repo통해 데이터 처리*/
) : ViewModel() {

    // 검색
    private val _searchList_st = mutableStateOf<List<Track>>(emptyList()) // 여러개의 객체 담고 있어서 List
    val searchList_st: State<List<Track>> get() = _searchList_st

    // 선택 -> stateflow
    private val _selectedTrack_st = MutableStateFlow<Track?>(null)
    val selectedTrack_st: StateFlow<Track?> get() = _selectedTrack_st

    /** 선택 트랙에서 Artist, Track명으로 get.Info 가져오기 -> StateFlow 로 변경 */
    private val _getAlbumImage_st = MutableStateFlow<String?>(null)
    val getAlbumImage_st: StateFlow<String?> get() = _getAlbumImage_st

    /** 선택 트랙에서 Artist, Track명으로 get.Info 가져오기 -> StateFlow 로 변경 */
    private val _getAlbumMap_st = MutableStateFlow<Map<String,String?>>(emptyMap())
    val getAlbumMap_st: StateFlow<Map<String,String?>> get() = _getAlbumMap_st

    /** 모든 트랙 데이터 상태 관리 */
    private val _getAllSavedTracks_st = mutableStateOf<List<TrackEntity>>(emptyList())
    val getAllSavedTracks_st: State<List<TrackEntity>> get() = _getAllSavedTracks_st

    private val _selectedTrackEntity_st = MutableStateFlow<TrackEntity?>(null)
    val selectedTrackEntity_st: StateFlow<TrackEntity?> get() = _selectedTrackEntity_st

    // 선택
    private val _memoContent_st = MutableStateFlow<MemoEntity?>(null)
    val memoContent_st: StateFlow<MemoEntity?> get() = _memoContent_st

    // db저장 성공 or 실패
    private var _saveResult_st = MutableStateFlow<Boolean?>(null)
    val saveResult_st : StateFlow<Boolean?> get() = _saveResult_st

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
        /** track 선택 시 즉시 상태 업데이트*/
        getAlbumPoster_vm() // 앨범 포스터 불러오기
        _selectedTrack_st.value = track
    }

    fun selectTrackEntity_vm(trackEntity: TrackEntity) {
        //imgurl 사용하여 image2 객체 생성 후 리스트로 변환
        val imgList = listOf(
            Image2(url = trackEntity.imageUrl, size = "extralarge")
        )
        // 변환된 이미지 리스트를 로그로 출력
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

    // 선택한 트랙으로 앨범포스터 가져오기
    fun getAlbumPoster_vm() {
        val selectedImageInfo = _selectedTrack_st.value ?: return

        _getAlbumImage_st.value = null

        viewModelScope.launch(Dispatchers.Default) {
            Log.d("sj_vm(st) GETPOSTER", "Running on thread: ${Thread.currentThread().name}")

            val albumInfo = repository.getAlbumPoster_impl(
                selectedImageInfo.name ?: "트랙",
                selectedImageInfo.artist ?: "아티스트"
            )
            if (albumInfo != null) {
                Log.d("getting album post", "album < ${albumInfo} >")
                val albumImageUrl = albumInfo.image.find { it.size == "extralarge" }?.url
                withContext(Dispatchers.Main) {
                    _getAlbumImage_st.value = albumImageUrl
                }
            } else {
                Log.e("album info error", "fail to get info $")
            }
            Log.d("sj_vm(en) GETPOSTER", "Running on thread: ${Thread.currentThread().name}")
        }
    }

    // 1006 추가 로직 URL -> MAP으로
    fun fetchTrackAndUrl_vm(track : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val trackINFO = repository.getMusic_impl(track)

            // 트랙 이름 기반으로 api 2 호출하여 이미지 가져오기
            val albumImg : Map<String, String?> = trackINFO.associate { url ->
                val posterUrl = repository.getAlbumPoster_impl(
                    url.name ?: "fail to load track",
                    url.artist ?: "fail to load artist"
                )
                // 트랙명 key, url value
                (url.name ?: "track_key") to (posterUrl?.image?.find { it.size == "extralarge" }?.url?: "url error vv")
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
                        memoContent = "오늘의 음악을 기록하세요 !"
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

    //////////////////
    // 트랙 선택 시 trackId 이용하여 memoentity 불러오기
    fun getMmUseID_vm(trackId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                memoRepository.getMemo_impl(trackId).collect() { mm ->
                    _memoContent_st.value = mm
                }
            } catch (e: Exception) {
                Log.e("test", "${e.message}")
            }
        }
    }
    fun loadTrackID_vm(trackEntity: TrackEntity) {
        _selectedTrackEntity_st.value = trackEntity
        getMmUseID_vm(trackEntity.trackId) // 자동 생성된 trackId로 MemoEntity 조회
    }
    //////////////////////

    // 검색 페이지 검색 값 초기화
    fun clearSearchResults() {
        _searchList_st.value = emptyList()  // 검색 결과 초기화
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