package com.sooj.today_music.presentation

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sooj.today_music.R


@Composable
fun SearchPageScreen(navController: NavController, musicViewModel: MusicViewModel) {
    val searchList by musicViewModel.searchList_st
    val getAlbumImage by musicViewModel.getAlbumImage_st.collectAsState()
    val getAlbumImg_Map by musicViewModel.getAlbumMap_st.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Image(imageVector = Icons.Outlined.LibraryMusic, contentDescription = "list",
                    Modifier.size(30.dp))
            }
            
            Text(text = "  <<- s e a r c h p a g e", fontFamily = FontFamily(Font(R.font.opensans_semibold),))
            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.padding(end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var text by remember {
                    mutableStateOf("")
                }
                BasicTextField(modifier = Modifier
                    .width(80.dp)
                    .weight(8f)
                    .border(color = Color.Transparent, width = 1.dp)
                    .background(Color.LightGray),
                    value = text, onValueChange = { text = it },
                    textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                        ) {
                            if (text.isEmpty()) {
                                Text(
                                    text = "오늘의 노래를 기록하세요 !",
                                    style = TextStyle(color = Color.Black),
                                    fontWeight = FontWeight.ExtraLight,
                                    fontFamily = FontFamily(Font(R.font.sc_dream_4),)
                                )
                            } else {
                            }
                            innerTextField() // 실제 텍스트 입력 필드
                        }
                    } // decorationBox
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = {
//                    musicViewModel.getMusic_vm(text)
                    musicViewModel.test2(text)
                    musicViewModel.fetchTrackAndUrl_vm(text)

                }) {
                    Image(imageVector = Icons.Outlined.Search, contentDescription = "search",
                        Modifier.size(35.dp))
                }
            } // row
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
            ) {
                items(searchList.size) { index ->
                    val track = searchList[index]
                    val albumUrl = getAlbumImg_Map[track.name] ?: R.drawable.yumi

                    Column(
                        modifier = Modifier
                            .padding(7.dp)
                            .fillMaxWidth()
                            .clickable {
                                // 다른 페이지로 이동
                                navController.navigate("select_page")

                                // 클릭 시, Viewmodel에 선택된 트랙 저장
                                musicViewModel.selectTrack_vm(track)

                                // new 추가 !@@@@@@@@@@@@@@22
                                musicViewModel.selectedTrack_st
                                musicViewModel.getAlbumPoster_vm()

                                Log.d(
                                    "1 Storing selected tracks in ViewModel",
                                    "saved ${musicViewModel.selectedTrack_st.value} &"
                                )
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // 1
//                        AsyncImage(
//                            model = ImageRequest.Builder(LocalContext.current)
//                                .data(
//                                    track.image?.find { it.size == "extralarge" }?.url?.takeIf { it.isNotEmpty() }
//                                        ?: R.drawable.yumi // URL이 비어 있으면 기본 이미지 리소스를 사용
//                                )
//                                .build(),
//                            contentDescription = null
//                        )

                        //2
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(getAlbumImage ?: R.drawable.yumi // URL이 비어 있으면 기본 이미지 리소스를 사용
                                )
                                .build(),
                            contentDescription = null
                        )

//                        //3
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(
                                    albumUrl ?: R.drawable.yumi // URL이 비어 있으면 기본 이미지 리소스를 사용
                                )
                                .build(),
                            contentDescription = null
                        )
//                        AsyncImage(model = albumUrl, contentDescription = "map")
                        Spacer(modifier = Modifier.height(8.dp))
                        /** 트랙명 */
                        Text(
                            text = track.name.toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily(Font(R.font.opensans_semibold),),
                            modifier = Modifier.align(Alignment.CenterHorizontally) // 텍스트 중앙 정렬
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        /** 아티스트명 */
                        Text(
                            text = track.artist.toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.opensans_semibold),),
                            modifier = Modifier.align(Alignment.CenterHorizontally) // 텍스트 중앙 정렬
                        )
                    }
                } // index
            }
        }
    }
}

@Preview
@Composable
fun WritePostPreview() {
    val navController = rememberNavController()
    SearchPageScreen(navController, hiltViewModel())
}