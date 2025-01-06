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
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.sooj.today_music.ui.theme.Today_MusicTheme
import dagger.hilt.android.AndroidEntryPoint

//


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
                CustomAppTheme {
                    //
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

                                val trackId = entry.arguments?.getInt("trackId") ?: return@composable
                                DetailPageScreen(navController, musicViewModel, memoViewModel) }

                            composable(Screen.WritePost.route) { SearchPageScreen(navController, musicViewModel) }
                            composable(Screen.EditDetailPage.route) { EditDetailPageScreen(navController, musicViewModel, memoViewModel)}
                            composable(Screen.SelectPage.route) { SelectPageScreen(navController, musicViewModel)}
                        }
                    }
                    //
                }

            }
        }
    }
    @Composable
    fun CustomAppTheme(content: @Composable () -> Unit) {
        // 다크 모드에서도 primary 색상 등을 검정으로 고정
        val customColors = MaterialTheme.colorScheme.copy(
            onBackground = Color.Black, // 기본 텍스트 색상
            onSurface = Color.Black     // 카드나 서피스 텍스트 색상
        )

        val customTypography = MaterialTheme.typography.copy(
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            bodyMedium = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
            bodySmall = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
        )

        MaterialTheme(
            colorScheme = customColors,
            typography = customTypography,
            content = content
        )
    }
}


//                        composable("detail_page/{trackId}") { entry ->
//                            val trackId = entry.arguments?.getString("trackId")?.toInt() ?: 0
//                            DetailPageScreen(navController, musicViewModel,memoViewModel, trackId)
//                        }