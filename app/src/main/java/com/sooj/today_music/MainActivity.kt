package com.sooj.today_music

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sooj.today_music.presentation.DetailPageScreen
import com.sooj.today_music.presentation.EditDetailPageScreen
import com.sooj.today_music.presentation.PosterListScreen
import com.sooj.today_music.presentation.SearchPageScreen
import com.sooj.today_music.presentation.SearchViewModel
//import com.sooj.today_music.presentation.memoViewModel
import com.sooj.today_music.ui.theme.Today_MusicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val searchViewModel : SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

//            val musicViewModel =
//                ViewModelProvider(this)[SearchViewModel::class.java] // 초기화, 인스턴스 생성
            Today_MusicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val musicViewModel = hiltViewModel<SearchViewModel>()
                    NavHost(navController = navController, startDestination = "poster_list") {

                        composable(Screen.PosterList.route) { PosterListScreen(navController, musicViewModel) }
                        composable(Screen.DetailPage.route) { DetailPageScreen(navController, musicViewModel) }
                        composable(Screen.WritePost.route) { SearchPageScreen(navController, musicViewModel) }
                        composable(Screen.EditDetailPage.route) { EditDetailPageScreen(navController, musicViewModel)}
                    }
                }
            }
        }
    }
}


