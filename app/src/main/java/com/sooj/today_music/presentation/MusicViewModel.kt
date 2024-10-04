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
    private val _searchList = mutableStateOf<List<Track>>(emptyList()) // 여러개의 객체 담고 있어서 List
    val searchList: State<List<Track>> get() = _searchList

    // 선택
    private val _selectedTrack = mutableStateOf<Track?>(null)
    val selectedTrack: State<Track?> get() = _selectedTrack

    /** 선택 트랙에서 Artist, Track명으로 get.Info 가져오기 -> StateFlow 로 변경 */
    private val _getAlbumImage = MutableStateFlow<String?>(null)
    val getAlbumImage: StateFlow<String?> get() = _getAlbumImage

    /** 모든 트랙 데이터 상태 관리 */
    private val _getAllSavedTracks = mutableStateOf<List<TrackEntity>>(emptyList())
    val getAllSavedTracks: State<List<TrackEntity>> get() = _getAllSavedTracks

    private val _selectedTrackEntity = MutableStateFlow<TrackEntity?>(null)
    val selectedTrackEntity: StateFlow<TrackEntity?> get() = _selectedTrackEntity

    // 선택
    private val _memoContent = MutableStateFlow<MemoEntity?>(null)
    val memoContent: StateFlow<MemoEntity?> get() = _memoContent

    // db저장 성공 or 실패
    private var _saveResult = MutableStateFlow<Boolean?>(null)
    val saveResult : StateFlow<Boolean?> get() = _saveResult


    /** track을 기반으로 음악 정보를 검색하고, 그 결과를 viewmodel 상태로 저장 */
    fun getMusic_vm(track: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("sj_vm(st) GETMUSIC", "Running on thread: ${Thread.currentThread().name}")
            try {
                val trackInfo = repository.getMusic_impl(track)
                withContext(Dispatchers.Main) {
                    _searchList.value = trackInfo
                }
            } catch (e: Exception) {
                Log.e("sj VIEWMODEL ERROR!!", "ERROR FETCHING TRACK INFO ${e.message}")
            }
            Log.d("sj_vm(en) GETMUSIC", "Running on thread: ${Thread.currentThread().name}")
        }
        // getLoadAlbumPoster()
    }

    // 선택한 트랙
    fun selectTrack_vm(track: Track) {
        /** track 선택 시 즉시 상태 업데이트*/
        getAlbumPoster_vm() // 앨범 포스터 불러오기
        _selectedTrack.value = track
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
        _selectedTrack.value = convertTrack
        Log.d("select Track", "Selected track from entity updated: ${_selectedTrack.value}")
    }

    // 선택한 트랙으로 앨범포스터 가져오기
    fun getAlbumPoster_vm() {
        val selectedImageInfo = _selectedTrack.value ?: return

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
                    _getAlbumImage.value = albumImageUrl
                    Log.d(
                        "sj_vm getposter withcontext",
                        "Running on thread: ${Thread.currentThread().name}"
                    )
                }
            } else {
                Log.e("album info error", "fail to get info $")
            }
            Log.d("sj_vm(en) GETPOSTER", "Running on thread: ${Thread.currentThread().name}")
        }
    }

    // Dao에 저장된 데이터 불러오는 메서드
    fun getAllTracks_vm() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _getAllSavedTracks.value = repository.getAllTracks_impl()
                Log.d("sj--call all data", "tracks load ${_getAllSavedTracks.value.size} gut ")
            } catch (e: Exception) {
                Log.e("sj--call data_error", "트랙s 로드 ${e.message} 오류")
            }
            Log.d("sj vm GETALL", "Running on thread: ${Thread.currentThread().name}")
        }
    }

    // 선택된 트랙을 데이터베이스에 저장
    fun saveSelectedTrack_vm() {
        val trackToSave = _selectedTrack.value ?: return
        val imgToSave = _getAlbumImage.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val trackEntity = TrackEntity(
                    trackName = trackToSave.name ?: return@launch,
                    artistName = trackToSave.artist ?: return@launch,
                    imageUrl = imgToSave,
                    saveAt = System.currentTimeMillis()
                )
                val memoEntity = MemoEntity(
                    trackId = trackEntity.trackId,
                    memoContent = "오늘의 음악을 기록하세요 !"
                )
                repository.saveSelectedTrack_impl(trackEntity, memoEntity) // db저장 코드
                Log.d("sj--db save", "track is ${trackEntity} gut")
                _saveResult.value = true
            } catch (e: Exception) {
                Log.e("sj--db error", "track is ${e.message} error")
                _saveResult.value = false
            }
        }
    }

    //////////////////
    // 트랙 선택 시 trackId 이용하여 memoentity 불러오기
    fun getMmUseID_vm(trackId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                memoRepository.getMemo_impl(trackId).collect() { mm ->
                    _memoContent.value = mm
                }
            } catch (e: Exception) {
                Log.e("test", "${e.message}")
            }
        }
    }
    fun loadTrackID_vm(trackEntity: TrackEntity) {
        _selectedTrackEntity.value = trackEntity
        getMmUseID_vm(trackEntity.trackId) // 자동 생성된 trackId로 MemoEntity 조회
    }
    //////////////////////



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