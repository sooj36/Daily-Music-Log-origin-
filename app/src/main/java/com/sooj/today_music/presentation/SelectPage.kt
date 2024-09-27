package com.sooj.today_music.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.sooj.today_music.R

@Composable
fun SelectPageScreen(navController: NavController, musicViewModel: MusicViewModel) {
    val selectedTrack by musicViewModel.selectedTrack
    val context = LocalContext.current // localcontext로 context 가져오기

    /** 1. 선택한 트랙 가져오기 */
    val getImageUrl by musicViewModel.getAlbumImage
    val imgURL = remember { getImageUrl }

    /** 2. 선택한 트랙 가져오기 */
    val loadTracks by musicViewModel.getAllSavedTracks

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(text = "< TRACK INFO >")

            LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                item {
                    selectedTrack?.let { trackInfo ->
                        if (imgURL != null) {
                            AsyncImage(model = imgURL, contentDescription = "poster")
                        } else {
                            Image(
                                painterResource(id = R.drawable.img),
                                contentDescription = "null img"
                            )
                        }
                    
                        Text(text = trackInfo?.artist ?: "아티스트")
                        Text(text = trackInfo?.name ?: "트랙")
                    }
                }

            }
        }
    }

}

@Preview
@Composable
fun SelectPagePreview() {
    val navController = rememberNavController()
    PosterListScreen(navController, hiltViewModel())
}