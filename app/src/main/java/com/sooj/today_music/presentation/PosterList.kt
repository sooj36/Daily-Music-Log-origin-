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
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PosterListScreen(navController: NavController, musicViewModel: SearchViewModel) {
    val selectedTrack by musicViewModel.selectedTrack /** 선택된 트랙 가져오기 */
    val getImageUrl by musicViewModel.getAlbumImage /** 앨범 포스터 가져오기*/
    val loadTracks by musicViewModel.allTracks /** 모든 트랙 가져오기*/

    LaunchedEffect(Unit) {


        withContext(Dispatchers.Default) {
            Log.d("sj_data1 ↓", "${Thread.currentThread().name}")
            musicViewModel.getAllTracks_vm() // 트랙 데이터를 가져옴
            Log.d("sj_data2 ↑", "${Thread.currentThread().name}")
        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Column {
            Text(text = "저장된 트랙은 총 ${loadTracks.size}개 입니다")

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


                Image(imageVector = Icons.Default.SaveAs, contentDescription = "getTrackData",
                    modifier = Modifier.clickable {
//                        musicViewModel.saveSelectedTrack()
                    })
            }
            Spacer(modifier = Modifier.height(15.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
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
                                Log.d("sj--image", "image URL: ${getImageUrl}")
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
        } //column
    }
}

@Preview
@Composable
fun PosterListPreview() {
    val navController = rememberNavController()
    PosterListScreen(navController, hiltViewModel())
}