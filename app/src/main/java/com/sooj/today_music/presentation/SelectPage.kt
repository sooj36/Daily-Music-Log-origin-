package com.sooj.today_music.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SettingsBackupRestore
import androidx.compose.material.icons.outlined.SystemUpdateAlt
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
fun SelectPageScreen(navController: NavController, musicViewModel: MusicViewModel) {
    val selectedTrack by musicViewModel.selectedTrack_st.collectAsState()
    val context = LocalContext.current // localcontext로 context 가져오기
    val saveResult = musicViewModel.saveResult_st.collectAsState()

    val getUrl by musicViewModel.getAlbumMap_st.collectAsState()
    val test = getUrl[selectedTrack?.name]
    val imgURL = remember { test }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
            .background(Color(0xFFEDEDE3))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(imageVector = Icons.Outlined.SettingsBackupRestore,
                    contentDescription = "edit",
                    Modifier.clickable { navController.popBackStack() })

                Image(imageVector = Icons.Outlined.SystemUpdateAlt,
                    contentDescription = "getTrackData",
                    modifier = Modifier.clickable {
                        musicViewModel.saveSelectedTrack_vm()
                        saveResult.value.let { success ->
                            try {
                                if (success == true) {
                                    Toast.makeText(context, "Track 저장 완", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(context, "DB 저장 실패 ${error("")}", Toast.LENGTH_LONG).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "-> ${e.message} <-", Toast.LENGTH_LONG).show()
                                Log.e("@save result", "save result ${e.message}")
                            }
                        }
                        navController.navigate("poster_list")
                    }
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),// Optional padding
            ) {
                item {
                    selectedTrack?.let { trackInfo ->
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                                Log.d("@@imgURL", "image URL: ${imgURL}")
                                AsyncImage(
                                    model = imgURL ?: R.drawable.yumi,
                                    contentDescription = "poster",
                                    modifier = Modifier
                                        .size(200.dp)
                                )

                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = trackInfo.artist ?: "아티스트",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )

                            Text(
                                text = trackInfo.name ?: "트랙",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        } // c
    }
}

@Preview
@Composable
fun SelectPagePreview() {
    val navController = rememberNavController()
    PosterListScreen(navController, hiltViewModel())
}