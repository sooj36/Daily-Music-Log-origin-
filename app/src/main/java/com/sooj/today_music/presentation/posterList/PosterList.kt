package com.sooj.today_music.presentation.posterList

import android.content.Intent
import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.QueueMusic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.sooj.today_music.R
import com.sooj.today_music.presentation.MusicViewModel
import com.sooj.today_music.room.TrackEntity
import com.sooj.today_music.ui.theme.iconColor
import com.sooj.today_music.ui.theme.textColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PosterListScreen(navController: NavController, musicViewModel: MusicViewModel) {
    // 저장된 트랙 개수를 불러오기
//    LaunchedEffect(key1 = true) {
//        Log.d("soj", "LaunchedEffect triggered in PosterListScreen")
//        musicViewModel.getAllSavedTracks_st
//    }

    /** 2) 앨범 포스터 가져오기 */
    val loadTracks by musicViewModel.getAllSavedTracks_st.collectAsState()

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
                    imageVector = Icons.Outlined.Cloud,
                    contentDescription = "YOUTUBE",
                    Modifier
                        .size(40.dp)
                        .clickable {
                            //

                        },
                    colorFilter = ColorFilter.tint(iconColor)
                )

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

            Bookmark(navController, musicViewModel = musicViewModel, loadTracks)

        }
    }
}


@Preview
@Composable
fun PosterListPreview() {
    val navController = rememberNavController()
    PosterListScreen(navController, hiltViewModel())
}