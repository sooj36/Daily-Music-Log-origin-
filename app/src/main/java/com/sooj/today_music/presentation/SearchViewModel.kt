package com.sooj.today_music.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooj.today_music.BuildConfig
import com.sooj.today_music.data.ApiService_EndPoint
import com.sooj.today_music.data.RetrofitInstance_build
import com.sooj.today_music.domain.Constant
import com.sooj.today_music.domain.Track
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SearchViewModel : ViewModel() {

    private val _searchList = mutableStateOf<List<Track>>(emptyList())
    val searchList : State<List<Track>> = _searchList

    private val musicApi = RetrofitInstance_build.musicApi

    fun getMusic(track: String) {
        Log.i("track", track)

        // coroutines
        viewModelScope.launch {
            val response = musicApi.getMusicSearch(
                "track.search",
                track,
                BuildConfig.LAST_FM_API_KEY,
                "json" )
            if (response.isSuccessful) {
                // 응답 성공 시,
                val musicModel = response.body()
                Log.d("API RESPONSE," ,musicModel.toString())

                response.body()?.results?.trackmatches?.track?.let { tracks ->
                    _searchList.value = tracks /** searchlist에 값을 할당*/

                    tracks.forEach {track ->
                        Log.d("API RESPONSE foreach", "Track: ${track.name}, Artist: ${track.artist}")
                    }
                } // track




            } else {
                Log.i("Response 에러", response.message())
                Log.e("API Error", "Error code: ${response.code()}, message: ${response.message()}")
            }
        }
    }
}