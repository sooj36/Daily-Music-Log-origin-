package com.sooj.today_music.presentation.posterList

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sooj.today_music.R
import com.sooj.today_music.presentation.MusicViewModel
import com.sooj.today_music.ui.theme.textColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Bookmark(navController: NavController, musicViewModel: MusicViewModel) {
    // 룸에서 가져온 데이터
    val getAllSaveTracks by musicViewModel.getAllSavedTracks_st

    var showDialog by remember { mutableStateOf(false) }

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

                            showDialog = true
//                            navController.navigate("detail_page")
//                            // 데이터 전달
//                            musicViewModel.selectTrackEntity_vm(trackEntity) // img, artist, track
//                            musicViewModel.loadTrackID_vm(trackEntity) // memo
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
                            textAlign = TextAlign.Center

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

            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false}) {
                    Surface (
                        shape = RoundedCornerShape(9.dp),
                        modifier = Modifier.padding(18.dp),
                        color = Color.White
                    ) {
                        Column(  modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "${trackEntity.artistName}의 \n ${trackEntity.trackName}",
                                textAlign = TextAlign.Center)

                            Spacer(modifier = Modifier.height(20.dp))

                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(onClick = {
                                    // Detail Page로 이동
                                    navController.navigate("detail_page")
                                    musicViewModel.selectTrackEntity_vm(trackEntity)
                                    musicViewModel.loadTrackID_vm(trackEntity)
                                    showDialog = false
                                },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Blue, // 버튼 배경색
                                        contentColor = Color.White // 버튼 텍스트 색상
                                    ) ){
                                    Text("메모 작성")
                                }
                            }

                            Spacer(modifier = Modifier.height(3.dp))

                            Button(onClick = {
                                // YouTube 검색
                                val query = "${trackEntity.trackName} ${trackEntity.artistName}"
                                val url = "https://www.youtube.com/results?search_query=${Uri.encode(query)}"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                navController.context.startActivity(intent)
                                showDialog = false
                            },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Gray, // 버튼 배경색
                                    contentColor = Color.White // 버튼 텍스트 색상
                                )

                            ) {
                                Text("YouTube 검색")
                            }
                        }
                    }
                }
            }
        }
    }
}
