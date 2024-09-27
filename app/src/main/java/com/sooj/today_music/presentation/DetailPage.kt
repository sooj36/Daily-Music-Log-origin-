package com.sooj.today_music.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.StickyNote2
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sooj.today_music.R

@Composable
fun DetailPageScreen(
    navController: NavController,
    musicViewModel: MusicViewModel,
//                     memoViewModel: memoViewModel = hiltViewModel()
) {
    /** 클릭한 트랙 가져오기 */
    val clickedTrack by musicViewModel.selectedTrack
    val getImageUrl by musicViewModel.getAlbumImage
    val imgUrl = remember { getImageUrl}
//    val getMemo by memoViewModel.memoContent
    Log.d("bring to clicked track", "click info-> ${clickedTrack} >")

    val scrollState = rememberScrollState() // 스크롤 상태 기억

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Image(imageVector = Icons.Default.LibraryMusic, contentDescription = "NoteList")
                }

                IconButton(onClick = {
                    // 이미 동일한 페이지에 있을 때 다시 네비게이션 되지 않게
                    if (navController.currentDestination?.route != "edit_detail_page") {
                        navController.navigate("edit_detail_page")
                    }
                    }) {
                    Image(imageVector = Icons.Default.StickyNote2, contentDescription = "edit")
                }
            }
            Box(modifier = Modifier) {
                Column(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (imgUrl != null) {
                        Log.d("detail_image", "image_URL: ${imgUrl}")
                        AsyncImage(model = imgUrl, contentDescription = "image", modifier = Modifier.size(200.dp))
                    } else {
                        Image(painterResource(id = R.drawable.img), contentDescription = "error")
                    }

                    Text(
                        text = clickedTrack?.artist ?: "알 수 없 는 아티스트",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(25.dp)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = clickedTrack?.name ?: "알 수 없 는 제목",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(21.dp)
                            .padding(start = 8.dp)
                    )
                    Card(
                        shape = RoundedCornerShape(30.dp),
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(20.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(20.dp),
                            text = "Hello, hello\n" +
                                    "I'm not where I'm supposed to be\n" +
                                    "I hope that you're missin' me\n" +
                                    "'Cause it makes me feel young\n" +
                                    "Hello, hello\n" +
                                    "Last time that I saw your face\n" +
                                    "Was recess in second grade\n" +
                                    "And it made me feel young\n"

                        )
                    }

                }
            } // box

        }
    }
}