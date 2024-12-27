package com.sooj.today_music.presentation

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

import com.sooj.today_music.R
import com.sooj.today_music.ui.theme.Pink40
import com.sooj.today_music.ui.theme.Pink80
import com.sooj.today_music.ui.theme.Purple40
import com.sooj.today_music.ui.theme.searchBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

@Composable
fun SearchPageScreen(navController: NavController, musicViewModel: MusicViewModel) {
    val searchList by musicViewModel.searchList_st.collectAsState() //
    val getAlbumImg_Map by musicViewModel.getAlbumMap_st.collectAsState() // poster
    var text by remember {
        mutableStateOf("")
    }
    val endTime = remember { mutableStateOf(0L) }
    val loadTime = remember { mutableStateOf(0L) }
    val startTime by musicViewModel.startTime.collectAsState()

    if (searchList.isNotEmpty() && endTime.value == 0L) {
        endTime.value = System.currentTimeMillis()
        loadTime.value = endTime.value - startTime
        Log.d("sjsj", "UI LOAD ${loadTime.value}ms")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            IconButton(onClick = {
                navController.popBackStack()
                musicViewModel.clearSearchResults() // 검색 값 초기화
            }) {
                Image(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "list",
                    Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(modifier = Modifier
                    .width(70.dp)
                    .weight(3f)
                    .border(color = searchBar, width = 3.dp)
                    .background(searchBar),
                    value = text,
                    onValueChange = { text = it },
                    textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.padding(8.dp)
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically, // 아이콘과 텍스트 세로 중앙 정렬
                                horizontalArrangement = Arrangement.Start // 아이콘과 텍스트가 왼쪽에 배치되도록
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search, // 기본 검색 아이콘
                                    contentDescription = "Search Icon", // 아이콘 설명
                                    modifier = Modifier.padding(end = 8.dp) // 아이콘과 텍스트 사이에 여백 추가
                                )
                                if (text.isEmpty()) {
                                    Text(
                                        text = "오늘의 노래를 검색하세요 !",
                                        style = TextStyle(color = Color.DarkGray),
                                        fontWeight = FontWeight.ExtraLight,
                                        fontFamily = FontFamily(Font(R.font.paperlogy_4regular))
                                    )
                                }
                                innerTextField()
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.width(6.dp))

                IconButton(onClick = {
                    musicViewModel.clearMap()
                    musicViewModel.getMusic_vm(text) // 검색 결과

                    musicViewModel.fetchTrackAndUrl_vm(text) // Poster Map으로
                    // 로그에 결과 요청 값 두번씩 찍히는 이유
                }) {
                    Image(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "search",
                        Modifier.size(35.dp)
                    )
                }
            } // row

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
            ) {
                items(searchList.size) { index ->
                    val track = searchList[index] // track, artist
                    val albumUrl = getAlbumImg_Map[track.name] // poster

                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .border(1.dp, Color.Transparent, RoundedCornerShape(70.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent) // 배경색 설정
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(7.dp)
                                .fillMaxWidth()
                                .clickable {
                                    // 다른 페이지로 이동
                                    navController.navigate("select_page")

                                    musicViewModel.selectTrack_vm(track) // track, artist

                                    musicViewModel.fetchTrackAndUrl_vm(track.name) // track 기반 url 요청 함수라 필요없음

                                },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            //로딩 시간 설정
                            var isLoading by remember { mutableStateOf(true) }
                            var coroutineScope = rememberCoroutineScope()

                            LaunchedEffect(Unit) {
                                coroutineScope.launch {
                                    delay(5000)
                                    isLoading = false
                                }
                            }

                            if (albumUrl != null) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(albumUrl)
//                                    .diskCachePolicy(CachePolicy.DISABLED)  // 캐싱 비활성화
                                        .diskCachePolicy(CachePolicy.ENABLED) // 캐싱 활성화
                                        .build(),
                                    contentDescription = "최종 이미지",
                                    onSuccess = { isLoading = false },
                                    onError = { isLoading = false },
                                    modifier = Modifier
                                        .size(150.dp) // 원하는 크기로 설정
                                        .clip(RoundedCornerShape(16.dp)) // 둥근 모서리 적용

                                )
                            } else {
                                CircularProgressIndicator(modifier = Modifier.size(48.dp))
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            /** 트랙명 */
                            Text(
                                text = track.name,
                                fontSize = 14.sp,
//                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily(Font(R.font.paperlogy_5medium)),
                                modifier = Modifier.align(Alignment.Start) // 텍스트 중앙 정렬
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            /** 아티스트명 */
                            Text(
                                text = track.artist.toString(),
                                fontSize = 14.sp,
//                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily(Font(R.font.paperlogy_5medium)),
                                modifier = Modifier.align(Alignment.Start) // 텍스트 중앙 정렬
                            )
                        }
                    } //card
                } // index
            }
        }
    }
}
