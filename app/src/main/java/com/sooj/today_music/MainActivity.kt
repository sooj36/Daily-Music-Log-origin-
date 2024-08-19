package com.sooj.today_music

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sooj.today_music.presentation.DetailPageScreen
import com.sooj.today_music.presentation.PosterListScreen
import com.sooj.today_music.presentation.WritePostScreen
import com.sooj.today_music.ui.theme.Today_MusicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Today_MusicTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "poster_list") {

                        composable("poster_list") { PosterListScreen(navController = navController) }
                        composable("detail_page") { DetailPageScreen(navController = navController) }
                        composable("write_post") { WritePostScreen(navController = navController) }

                    }
                }
            }
        }
    }
}

