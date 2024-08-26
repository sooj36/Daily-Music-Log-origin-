package com.sooj.today_music.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooj.today_music.data.NetworkModule
import com.sooj.today_music.domain.SearchRepository
import com.sooj.today_music.domain.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
    /** viewmodel 생성 시 Hilt가 알아서 repo 제공해주고, 이 주입받은 repo통해 데이터 처리*/
) : ViewModel() {

    // 검색
    private val _searchList = mutableStateOf<List<Track>>(emptyList()) // 여러개의 객체 담고 있어서 List
    val searchList: State<List<Track>> get() = _searchList

    // 선택
    private val _selectedTrack = mutableStateOf<Track?>(null)
    val selectedTrack : State<Track?> get() = _selectedTrack


    /** _searchList.value <-- _searchList 값 가져와 외부에 노출
     * val searchList: State<List<Track>> get() = _searchList
    get() 커스텀 게터 */

    /** private val _infoList = mutableStateOf<List<Album>>(emptyList())
    val infoList: State<List<Album>> get() = _infoList */

//    private val musicApi2 = RetrofitInstance_build.musicApi
//    private val musicApi = NetworkModule.RetrofitInstance_build() <- retrofit 인스턴스 생성하지 말고 di 통해 사용할 것

    fun getMusic(track: String) {
        Log.i("track", track)

        viewModelScope.launch {
            try {
                val trackInfo = repository.getTrackInfo(track)
                _searchList.value = trackInfo
            } catch (e: Exception) {
                Log.e("VIEWMODEL ERROR !!", "ERROR FETCHING TRACK INFO ${e.message}")
            }
        }
    }

    // 선택한 트랙
    fun selectTrack(track: Track) {
        viewModelScope.launch {

        }
        _selectedTrack.value = track
        Log.d("선택한 트랙", "SELECTED TRACK : ${_selectedTrack.value}")
    }

// coroutines( data 의존성 있는 경우) //
//        viewModelScope.launch {
//            val response = musicApi.getMusicSearch(
//                "track.search",
//                track,
//                BuildConfig.LAST_FM_API_KEY,
//                "json"
//            )
//
//            if (response.isSuccessful) {
//                // 응답 성공 시,
//                val musicModel = response.body()
//                Log.d("API RESPONSE,", musicModel.toString())
//
//                response.body()?.results?.trackmatches?.track?.let { tracks ->
//                    _searchList.value = tracks
//                    /** searchlist에 값을 할당*/
//
//                    tracks.forEach { track ->
//                        Log.d(
//                            "API RESPONSE foreach",
//                            "Track: ${track.name}, Artist: ${track.artist}"
//                        )
//                    } // track
//                }
//            } else {
//                println("response error")
//                Log.e(
//                    "response error",
//                    "Error Code 에러 번호는 ! : ${response.code()}, message:${response.message()}"
//                )
//            }
//        } //viewModelScope
}