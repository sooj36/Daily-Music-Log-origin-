package com.sooj.today_music.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbIncandescent
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sooj.today_music.R

@Composable
fun PosterListScreen(navController: NavController, musicViewModel: SearchViewModel) {
    /** 선택된 트랙 가져오기 */
    val selectedTrack by musicViewModel.selectedTrack
    val getImageUrl by musicViewModel.getAlbumImage

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Column {

            selectedTrack?.let { info ->
                val artistName = info.artist ?: "알 수 없 는 아티스트"
                val trackName = info.name ?: "알 수 없는 트랙묭"
                Log.d("선택한 아티스트 및 트랙명", "${artistName}과 ${trackName}")

            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.navigate("write_post") }) {
                    Image(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = "posting",
                        Modifier.size(40.dp)
                    )

                }
                Text(text = " MY DAILY MUSIC RECORD <#3 ")
            }
            Spacer(modifier = Modifier.height(15.dp))

//            if (getImageUrl != null) {
//                Log.d("이미지이미지", "이미지 URL: ${getImageUrl}")
//                AsyncImage(model = getImageUrl, contentDescription = "image")
//            } else {
//                Image(painterResource(id = R.drawable.yumi), contentDescription = "error")
//            }


            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                items(1) {
                    selectedTrack?.let { trackInfo ->
                        Column(modifier = Modifier
                            .padding(5.dp)
                            .clickable {
                                navController.navigate("detail_page")
                            }) {

                            if (getImageUrl != null) {
                                Log.d("이미지이미지", "이미지 URL: ${getImageUrl}")
                                AsyncImage(model = getImageUrl, contentDescription = "image")
                            } else {
                                Image(
                                    painterResource(id = R.drawable.yumi),
                                    contentDescription = "error"
                                )
                            }

//                            AsyncImage(
//                                model = ImageRequest.Builder(LocalContext.current)
//                                    .data(
//                                        trackInfo?.image?.find { it.size == "extralarge" }?.url?.takeIf { it.isNotEmpty() }
//                                            ?: R.drawable.yumi // URL이 비어 있으면 기본 이미지 리소스를 사용
//                                    )
//                                    .build(),
//                                contentDescription = null
//                            )
                            Text(text = trackInfo.name ?: "알 수 없 는 제 목", fontSize = 19.sp)
                            Text(text = trackInfo.artist ?: "알수없는 아티스트", fontSize = 17.sp)
                        }
                    }
                }
            }
        } // column
    }
}

@Preview
@Composable
fun PosterListPreview() {
    val navController = rememberNavController()
    PosterListScreen(navController, hiltViewModel())
}