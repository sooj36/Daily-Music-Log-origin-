package com.sooj.today_music.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.SystemUpdateAlt
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.sooj.today_music.R

@Composable
fun PosterListScreen(navController: NavController, musicViewModel: MusicViewModel) {
    val selectedTrack by musicViewModel.selectedTrack

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
                text = "[ 저장된 트랙은 총 ${loadTracks.size}개 입니다 ] \n List Page",
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.navigate("write_post") }) {
                    Image(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = "posting",
                        Modifier.size(45.dp)
                    )

                }
                Text(
                    text = "MY DAILY MUSIC RECORD <3",
                    fontWeight = FontWeight.SemiBold
                )


                Image(imageVector = Icons.Default.SystemUpdateAlt,
                    contentDescription = "getTrackData",
                    modifier = Modifier.clickable { musicViewModel.saveSelectedTrack_vm()
                    Toast.makeText(context, "DB로 저장", Toast.LENGTH_LONG).show()}
                )
            }
            Spacer(modifier = Modifier.height(15.dp))

            Bookmark(musicViewModel = musicViewModel)

        } // c1
    }
}

@Composable
fun Bookmark(musicViewModel: MusicViewModel) {
    // 룸에서 가져온 데이터
    val getAllSaveTracks by musicViewModel.getAllSavedTracks

    // 그리드 뷰
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(getAllSaveTracks) { track ->
            Column(Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween) {
                AsyncImage(model = track.imageUrl, contentDescription = "img")
                track.trackName?.let { Text(text = it) }
                track.artistName?.let { Text(text = it) }
            }
        }
    }
}

@Preview
@Composable
fun PosterListPreview() {
    val navController = rememberNavController()
    PosterListScreen(navController, hiltViewModel())
}