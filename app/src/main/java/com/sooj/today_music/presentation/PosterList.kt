package com.sooj.today_music.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicVideo
import androidx.compose.material.icons.filled.SpatialTracking
import androidx.compose.material.icons.filled.SystemUpdateAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
    val selectedTrack by musicViewModel.selectedTrack
    val loadTrackID by musicViewModel.getAllSavedTracks

    val context = LocalContext.current // localcontext로 컨텍스트 가져오기

    /** 1) 선택된 트랙 가져오기 */
    val getImageUrl by musicViewModel.getAlbumImage
    val imgUrl = remember { getImageUrl }
    Log.d("Compose Recomposition", "PosterListScreen recomposed")

    // 저장된 트랙 개수를 불러오기
    LaunchedEffect(Unit) {
        musicViewModel.getAllTracks_vm()
    }

    /** 2) 앨범 포스터 가져오기 */
    val loadTracks by musicViewModel.getAllSavedTracks

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Column {
            Text(
                text = "[총 ${loadTracks.size}개 기록 <@ ] \n 리스트 페이지",
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily(Font(R.font.sc_dream_3),)
            )

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
//                IconButton(onClick = { navController.navigate("write_post") }) {
//                    Image(
//                        imageVector = Icons.Default.MusicVideo,
//                        contentDescription = "posting",
//                        Modifier.size(45.dp)
//                    )
//                }

                Text(
                    text = "My Daliy MUSIC Record <3",
                    fontFamily = FontFamily(Font(R.font.opensans_semibold),),
                    fontStyle = FontStyle.Italic,
                    fontSize = 21.sp,
                    modifier = Modifier.clickable {
                        navController.navigate("write_post")
                    }
                )


//                Image(imageVector = Icons.Default.SystemUpdateAlt,
//                    contentDescription = "getTrackData",
//                    modifier = Modifier.clickable {
//                        musicViewModel.saveSelectedTrack_vm()
//                        Toast.makeText(context, "DB로 저장", Toast.LENGTH_LONG).show()
//                    })
            }
            Spacer(modifier = Modifier.height(15.dp))

            Bookmark(navController, musicViewModel = musicViewModel)

        } // c1
    }
}

@Composable
fun Bookmark(navController: NavController, musicViewModel: MusicViewModel) {
    // 룸에서 가져온 데이터
    val getAllSaveTracks by musicViewModel.getAllSavedTracks

    // 그리드 뷰
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(getAllSaveTracks) { trackEntity ->
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(15.dp)),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFC1CC)) // 배경색 설정
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clickable {
                            navController.navigate("detail_page")
                            // 데이터 전달
                            musicViewModel.selectTrackEntity_vm(trackEntity) // img, artist, track
                            musicViewModel.loadTrackID_vm(trackEntity) // trackId
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    AsyncImage(model = trackEntity.imageUrl, contentDescription = "img")
                    Spacer(modifier = Modifier.height(3.dp))
                    trackEntity.trackId?.let { trackId ->
                        Text(text = "Number : ${trackId.toString()}", fontFamily = FontFamily(Font(R.font.opensans_semibold),)) }
                    Spacer(modifier = Modifier.height(3.dp))
                    trackEntity.trackName?.let { Text(text = it, fontFamily = FontFamily(Font(R.font.opensans_semibold),), fontSize = 20.sp) }
                    Spacer(modifier = Modifier.height(3.dp))
                    trackEntity.artistName?.let { Text(text = it, fontFamily = FontFamily(Font(R.font.opensans_semibold),))}

                    Spacer(modifier = Modifier.height(3.dp))
                    // 저장 시간을 date로 변환하여 표시
                    val saveAt = trackEntity.saveAt
                    val dateFormat = SimpleDateFormat("yy년 MM월 dd일 HH:mm", Locale.getDefault())
                    val formattedDate = dateFormat.format(Date(saveAt))
                    Text(text = "${formattedDate}", fontFamily = FontFamily(Font(R.font.opensans_semibold),))
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