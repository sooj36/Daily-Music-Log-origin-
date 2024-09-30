package com.sooj.today_music

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.collection.mutableIntFloatMapOf
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sooj.today_music.presentation.DetailPageScreen
import com.sooj.today_music.presentation.EditDetailPageScreen
import com.sooj.today_music.presentation.PosterListScreen
import com.sooj.today_music.presentation.SearchPageScreen
import com.sooj.today_music.presentation.MusicViewModel
import com.sooj.today_music.presentation.SelectPageScreen
import com.sooj.today_music.presentation.MemoViewModel
//import com.sooj.today_music.presentation.memoViewModel
import com.sooj.today_music.ui.theme.Today_MusicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val musicViewModel : MusicViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d("Thread_main", "Running on thread: ${Thread.currentThread().name}")

//            val musicViewModel =
//                ViewModelProvider(this)[SearchViewModel::class.java] // 초기화, 인스턴스 생성
            Today_MusicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val musicViewModel = hiltViewModel<MusicViewModel>()
                    val memoViewModel = hiltViewModel<MemoViewModel>()

                    NavHost(navController = navController, startDestination = "poster_list") {

                        composable(Screen.PosterList.route) { PosterListScreen(navController, musicViewModel) }

                        composable(Screen.DetailPage.route) { entry ->
                            val trackId = entry.arguments?.getInt("trackId") ?: 0
                            DetailPageScreen(navController, musicViewModel, memoViewModel, trackId) }

                        composable(Screen.DetailPage.route) { DetailPageScreen(navController, musicViewModel, memoViewModel, trackId = 9) }
                        composable(Screen.WritePost.route) { SearchPageScreen(navController, musicViewModel) }
                        composable(Screen.EditDetailPage.route) { EditDetailPageScreen(navController, musicViewModel, memoViewModel)}
                        composable(Screen.SelectPage.route) { SelectPageScreen(navController, musicViewModel)}
                    }
                }
            }
        }
    }
}


//                        composable("detail_page/{trackId}") { entry ->
//                            val trackId = entry.arguments?.getString("trackId")?.toInt() ?: 0
//                            DetailPageScreen(navController, musicViewModel,memoViewModel, trackId)
//                        }