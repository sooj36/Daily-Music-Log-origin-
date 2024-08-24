package com.sooj.today_music.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooj.today_music.BuildConfig
import com.sooj.today_music.data.RetrofitInstance_build
import com.sooj.today_music.domain.Album
import kotlinx.coroutines.launch


class SearchViewModel : ViewModel() {

//    private val _searchList = mutableStateOf<List<Track2>>(emptyList())
//    val searchList: State<List<Track2>> = _searchList

    private val _infoList = mutableStateOf<List<Album>>(emptyList())
    val infoList: State<List<Album>> get() = _infoList

    private val musicApi = RetrofitInstance_build.musicApi

    fun getMusic(track: String) {
        Log.i("track", track)

        // coroutines
        viewModelScope.launch {
            val response2 = musicApi.getMusicSearch(
                "track.info",
                track,
                BuildConfig.LAST_FM_API_KEY,
                "json"
            )
            val response = musicApi.getMusicSearch(
                "track.getInfo",
                BuildConfig.LAST_FM_API_KEY,
                "jannabi",
                track,
                "json"
            )
            if (response.isSuccessful) {
                response.body()?.track?.album?.let { info ->
                    _infoList.value = listOf(info)
                }
                val infoModel = response.body()
                Log.d("API RESPONSE," , infoModel.toString())

//                // 응답 성공 시,
//                val musicModel = response.body()
//                Log.d("API RESPONSE," ,musicModel.toString())
//
//                response.body()?.results?.trackmatches?.track?.let { tracks ->
//                    _searchList.value = tracks /** searchlist에 값을 할당*/
//
//                    tracks.forEach {track ->
//                        Log.d("API RESPONSE foreach", "Track: ${track.name}, Artist: ${track.artist}")
//                    } // track2
            } else {
                Log.e(
                    "response error",
                    "Error Code : ${response.code()}, message:${response.message()}"
                )
            }
        }
    }
}
