package com.sooj.today_music.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.sooj.today_music.R

@Composable
fun DetailPageScreen(navController: NavController, musicViewModel: SearchViewModel) {
    /** 클릭한 트랙 가져오기 */
    val clickedTrack by musicViewModel.selectedTrack
    Log.d("클릭한 트랙 가져오기", "클릭 정보 : ${clickedTrack}")
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Row {
                Image(painterResource(id = R.drawable.back), contentDescription = "back",
                    modifier = Modifier.clickable { navController.popBackStack() })
            }

            Column {
                Text(text = "TITLE", modifier = Modifier.fillMaxWidth(), fontSize = 15.sp) // 노래제목
                Image(
                    painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
                ) // 앨범 포스터
                Text(text = "2024-08-19") // 등록시간
            }
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)) {
        items(1) {
            clickedTrack?.let { clickedInfo ->
                Column {
                    AsyncImage(
                        model = clickedInfo.image?.find { it.size == "large" }?.url,
                        contentDescription = null,
                        modifier = Modifier.height(160.dp)
                    )
                    Text(text = clickedInfo.artist ?: "알 수 없 는 아티스트")
                    Text(text = clickedInfo.name ?: "알 수 없 는 제목")
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailPagePreview() {
    val navController = rememberNavController()
    DetailPageScreen(navController, hiltViewModel())
}