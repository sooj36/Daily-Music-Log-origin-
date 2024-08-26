package com.sooj.today_music.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

@Composable
fun PosterListScreen(navController: NavController) {
    /**  ViewModel  */
    val musicViewModel: SearchViewModel = hiltViewModel()

    /** 선택된 트랙 가져오기 */
    val selectedTrack by musicViewModel.selectedTrack

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { navController.navigate("write_post") }) {
                Text(text = "포스팅")
            }
            Text(text = " P O S T E R  L I S T S C R E E N ")
            Spacer(modifier = Modifier.height(24.dp))

            Text(text = " P O S T E R  L I S T S C R E E N ")
            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            ) {
                items(1) {
                    selectedTrack?.let { track ->
                        Column {
                            Text(text = track?.artist ?: "알수없는 아티스트")
                            AsyncImage(
                                model = track?.image?.find { it.size == "extralarge" }?.url,
                                contentDescription = null,
                                modifier = Modifier.height(200.dp)
                            )
                            Text(text = track?.name ?: "알 수 없 는 제 목")
                        }
                    }
                }
            } //
        } // column
    }
}

@Preview
@Composable
fun PosterListPreview() {
    val navController = rememberNavController()
    PosterListScreen(navController)
}