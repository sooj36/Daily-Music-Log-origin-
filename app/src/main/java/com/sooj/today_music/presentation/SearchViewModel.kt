package com.sooj.today_music.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooj.today_music.BuildConfig
import com.sooj.today_music.data.RetrofitInstance_build
import com.sooj.today_music.domain.SearchRepository
import com.sooj.today_music.domain.Track
import kotlinx.coroutines.launch


class SearchViewModel : ViewModel() {
    private val _searchList = mutableStateOf<List<Track>>(emptyList())
    val searchList: State<List<Track>> get() = _searchList
    /** _searchList.value <-- _searchList 값 가져와 외부에 노출
     * val searchList: State<List<Track>> get() = _searchList
    get() 커스텀 게터*/

    /** private val _infoList = mutableStateOf<List<Album>>(emptyList())
    val infoList: State<List<Album>> get() = _infoList */

    private val musicApi = RetrofitInstance_build.musicApi

    fun getMusic(track: String) {
        Log.i("track", track)

        // coroutines
        viewModelScope.launch {
            val response = musicApi.getMusicSearch(
                "track.search",
                track,
                BuildConfig.LAST_FM_API_KEY,
                "json"
            )

            if (response.isSuccessful) {
                // 응답 성공 시,
                val musicModel = response.body()
                Log.d("API RESPONSE,", musicModel.toString())

                response.body()?.results?.trackmatches?.track?.let { tracks ->
                    _searchList.value = tracks
                    /** searchlist에 값을 할당*/

                    tracks.forEach { track ->
                        Log.d(
                            "API RESPONSE foreach",
                            "Track: ${track.name}, Artist: ${track.artist}"
                        )
                    } // track
                }
            } else {
                println("response error")
                Log.e(
                    "response error",
                    "Error Code 에러 번호는 ! : ${response.code()}, message:${response.message()}"
                )
            }
        }
    }
}
