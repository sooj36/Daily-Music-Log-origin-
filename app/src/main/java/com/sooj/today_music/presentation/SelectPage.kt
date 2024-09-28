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
import androidx.compose.material.icons.filled.SettingsBackupRestore
import androidx.compose.material.icons.filled.StickyNote2
import androidx.compose.material.icons.filled.SystemUpdateAlt
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    val selectedTrack by musicViewModel.selectedTrack
    val context = LocalContext.current // localcontext로 context 가져오기

    /** 1. 선택한 트랙 가져오기 */
    val getImageUrl by musicViewModel.getAlbumImage
    val imgURL = remember { getImageUrl }

    /** 2. 앨범 포스터 가져오기 */
    val loadTracks by musicViewModel.getAllSavedTracks
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Image(imageVector = Icons.Default.SettingsBackupRestore,
                    contentDescription = "edit",
                    Modifier.clickable { navController.popBackStack() })

                Image(imageVector = Icons.Default.SystemUpdateAlt,
                    contentDescription = "getTrackData",
                    modifier = Modifier.clickable { musicViewModel.saveSelectedTrack_vm()
                        Toast.makeText(context, "DB로 저장", Toast.LENGTH_LONG).show()
                    navController.navigate("poster_list")}
                )
            }

            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "<><><><><><><><><><><><><><><><><>",
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )

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
                                .fillMaxWidth()
                                .clickable { navController.navigate("detail_page") },
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            if (imgURL != null) {
                                Log.d("sj--imgURL", "image URL: ${imgURL}")
                                AsyncImage(model = imgURL,
                                    contentDescription = "poster",
                                    modifier = Modifier
                                        .size(200.dp))
                            } else {
                                Image(
                                    painterResource(id = R.drawable.img),
                                    contentDescription = "null img"
                                )
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = trackInfo?.artist ?: "아티스트",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )

                            Text(
                                text = trackInfo?.name ?: "트랙",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Text(
                                text = "<><><><><><><><><><><><><><><>",
                                fontSize = 19.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black)
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