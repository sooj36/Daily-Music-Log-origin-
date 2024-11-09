package com.sooj.today_music.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmAdd
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.AlarmAdd
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.sooj.today_music.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PosterListScreen(navController: NavController, musicViewModel: MusicViewModel) {
    // 저장된 트랙 개수를 불러오기
    LaunchedEffect(Unit) {
        musicViewModel.getAllTracks_vm()
    }

    /* 앨범 포스터 가져오기 */
    val loadTracks by musicViewModel.getAllSavedTracks_st
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDEDE3))
            .padding(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "[총 ${loadTracks.size}]",
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily(Font(R.font.sc_dream_3))
                )

                IconButton(onClick = { navController.navigate("alarm_page") }) {
                    Image(imageVector = Icons.Outlined.AlarmAdd, contentDescription = "NoteList",Modifier.size(28.dp))
                }


            }


            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {

                Text(
                    text = "my daliy MUSIC record <3",
                    fontFamily = FontFamily(Font(R.font.paperlogy_7bold)),
                    fontStyle = FontStyle.Italic,
                    fontSize = 21.sp,
                    modifier = Modifier.clickable {
                        navController.navigate("write_post")
                    }
                )

            }
            Spacer(modifier = Modifier.height(25.dp))

            Bookmark(navController, musicViewModel = musicViewModel)

        } // c1
    }
}

@Composable
fun Bookmark(navController: NavController, musicViewModel: MusicViewModel) {
    // 룸에서 가져온 데이터
    val getAllSaveTracks by musicViewModel.getAllSavedTracks_st

    // 그리드 뷰
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(getAllSaveTracks) { trackEntity ->

            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .border(3.dp, Color.LightGray, RoundedCornerShape(35.dp)),

                colors = CardDefaults.cardColors(containerColor = Color.Transparent) // 배경색 설정
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .clickable {
                            navController.navigate("detail_page")
                            // 데이터 전달
                            musicViewModel.selectTrackEntity_vm(trackEntity) // img, artist, track
//                            musicViewModel.loadTrackID_vm(trackEntity) // trackId
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    val saveAt = trackEntity.saveAt
                    val dateFormat = SimpleDateFormat("MM월 dd일", Locale.getDefault())
                    val formattedDate = dateFormat.format(Date(saveAt))
                    Text(
                        text = "${formattedDate}",
                        fontFamily = FontFamily(Font(R.font.opensans_semibold))
                    )

                    // 저장 시간을 date로 변환하여 표시
//                    val saveAt = trackEntity.saveAt
//                    val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm", Locale.getDefault())
//                    val formattedDate = dateFormat.format(Date(saveAt))
//                    Text(text = "${formattedDate}", fontFamily = FontFamily(Font(R.font.opensans_semibold),))

                    Spacer(modifier = Modifier.height(3.dp))
                    trackEntity.trackName?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily(Font(R.font.paperlogy_5medium)),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center // 텍스트 중앙 정렬
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    trackEntity.artistName?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.paperlogy_5medium)),
                            textAlign = TextAlign.Center // 텍스트 중앙 정렬
                        )
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    AsyncImage(model = trackEntity.imageUrl, contentDescription = "img")

                    Spacer(modifier = Modifier.height(10.dp))

                }
            } // card
        }
    }
}


@Preview
@Composable
fun PosterListPreview() {
    val navController = rememberNavController()
    PosterListScreen(navController, hiltViewModel())
}