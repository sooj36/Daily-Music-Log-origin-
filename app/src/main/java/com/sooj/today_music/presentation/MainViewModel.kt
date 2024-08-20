package com.sooj.today_music.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooj.today_music.data.RetrofitInstance
import com.sooj.today_music.domain.MusicSearchDataClass
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val apiService = RetrofitInstance.api
    val musicSearch : MutableState<List<MusicSearchDataClass>> = mutableStateOf(emptyList())

    fun getMusicSearch() {
        viewModelScope.launch {
            try {
                val response = apiService.getMusicSearch()
                if (response.isNotEmpty()) {
                    musicSearch.value = response
                }
            } catch (e : Exception) {
                println("에러")
            }
        }
    }

}