package com.sooj.today_music.presentation

import androidx.lifecycle.ViewModel
import com.sooj.today_music.domain.MemoRepository
import javax.inject.Inject

class memoViewModel @Inject constructor(
    private val memoRepository: MemoRepository
) : ViewModel() {


}