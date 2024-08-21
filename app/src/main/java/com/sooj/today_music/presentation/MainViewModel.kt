package com.sooj.today_music.presentation

import androidx.lifecycle.ViewModel
import com.sooj.today_music.data.ApiService2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class MainViewModel : ViewModel() {
//    private val _uiState get() = MutableStateFlow(ApiService2("",","))
    val uiState : StateFlow<ApiService2>
        get() {
            TODO()
        }


}