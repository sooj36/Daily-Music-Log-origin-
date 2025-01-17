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
import androidx.compose.material.icons.outlined.Adb
import androidx.compose.material.icons.outlined.MediaBluetoothOn
import androidx.compose.material.icons.outlined.QueueMusic
import androidx.compose.material.icons.outlined.SpeakerNotesOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.sooj.today_music.ui.theme.iconColor
import com.sooj.today_music.ui.theme.searchBar
import com.sooj.today_music.ui.theme.textColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PosterListScreen(navController: NavController, musicViewModel: MusicViewModel) {
    // 저장된 트랙 개수를 불러오기
    LaunchedEffect(key1 = true) {
        musicViewModel.getAllTracks_vm()
    }

    /** 2) 앨범 포스터 가져오기 */
    val loadTracks by musicViewModel.getAllSavedTracks_st


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
//            .background(Color(0xFFEDEDE3))
            .padding(12.dp)
    ) {
        Column {
            Text(
                text = "[총 ${loadTracks.size}]",
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(Font(R.font.sc_dream_3)),
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    imageVector = Icons.Outlined.QueueMusic,
                    contentDescription = "delete",
                    Modifier
                        .size(40.dp)
                        .clickable {
                            navController.navigate("write_post")},
                    colorFilter = ColorFilter.tint(iconColor)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            Bookmark(navController, musicViewModel = musicViewModel)

        }
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
                    .padding(5.dp),

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
                            musicViewModel.loadTrackID_vm(trackEntity) // memo
                        },
//                    horizontalAlignment = Alignment.CenterHorizontally, // 수평 정렬
                    horizontalAlignment = Alignment.Start, // 수평 정렬

                    ) {
                    // 1. YYMMDD
                    val saveAt = trackEntity.saveAt
                    val dateFormat = SimpleDateFormat("MM / dd", Locale.getDefault())
                    val formattedDate = dateFormat.format(Date(saveAt))
                    Text(
                        text = "${formattedDate}",
                        fontFamily = FontFamily(Font(R.font.paperlogy_5medium)),
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    // 4. TRACK POSTER
                    Box(
                        modifier = Modifier
                            .size(160.dp) // 크기 설정
                            .shadow(
                                elevation = 8.dp, // 그림자 높이
                                shape = RoundedCornerShape(16.dp), // 그림자 모양
                                clip = false // 그림자가 잘리지 않도록 설정
                            )
                            .clip(RoundedCornerShape(16.dp)) // 둥근 모서리 적용
                            .background(Color.White) // 이미지 배경색 (필요시)
                            .border(
                                width = 2.dp,
                                color = Color.Transparent, // 테두리 색상
                                shape = RoundedCornerShape(16.dp) // 테두리 모양
                            )
                    ) {
                        AsyncImage(
                            model = trackEntity.imageUrl,
                            contentDescription = "img",
                            modifier = Modifier
                                .fillMaxSize() // 부모 박스에 맞춤
                                .clip(RoundedCornerShape(16.dp)) // 이미지 모양 일치
                        )
                    }


                    Spacer(modifier = Modifier.height(1.dp))

                    // 2. 트랙 이름
                    trackEntity.trackName?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily(Font(R.font.paperlogy_5medium)),
                            fontSize = 13.sp,
//                            textAlign = TextAlign.Left // 텍스트 중앙 정렬
                        )
                    }

                    Spacer(modifier = Modifier.height(1.dp))

                    // 3. 아티스트 이름
                    trackEntity.artistName?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.paperlogy_5medium)),
                            textAlign = TextAlign.Center, // 텍스트 중앙 정렬,
                            color = textColor, // 텍스트 색상 설정
                            fontSize = 13.sp,

                        )
                    }

                    Spacer(modifier = Modifier.height(3.dp))

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