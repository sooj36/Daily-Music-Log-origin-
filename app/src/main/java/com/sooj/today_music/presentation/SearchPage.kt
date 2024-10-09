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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AllInclusive
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sooj.today_music.R


@Composable
fun SearchPageScreen(navController: NavController, musicViewModel: MusicViewModel) {

    val searchList by musicViewModel.searchList_st
    val getAlbumImage = ""
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
                    val albumUrl = getAlbumImg_Map[track.name]

                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .border(2.dp, Color.LightGray, RoundedCornerShape(10.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9E5DA)) // 배경색 설정
                    ) {
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
                                },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            //2 첫번째 데이터가 모두 로드
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(getAlbumImage ?: Icons.Outlined.Search // URL이 비어 있으면 기본 이미지 리소스를 사용
                                    )
                                    .diskCachePolicy(CachePolicy.DISABLED)
                                    .build(),
                                contentDescription = null,
                            )

                            //3 map으로 수정
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        albumUrl // URL이 비어 있으면 기본 이미지 리소스를 사용
                                    )
                                    .diskCachePolicy(CachePolicy.DISABLED)  // 캐싱 비 활성화
                                    .build(),
                                contentDescription = null
                            )

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
                    } //card

                } // index
            }
        }
    }
}

@Composable
fun AnimatedPreloader(modifier: Modifier = Modifier) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.animation_preloader
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
}

// lottie 수정 ver
@Composable
fun preLoader(albumUrl : String) {
    // coil의 asyncimgpainter 사용하여 이미지 상태 추적
    val painter = rememberAsyncImagePainter(model = albumUrl)

    Box {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                // 로딩 중이면 preloader
                AnimatedPreloader(modifier = Modifier.fillMaxSize())
            }
            is AsyncImagePainter.State.Success -> {
                // 로딩 완료 시 이미지 표시
                Image(painter = painter, contentDescription = null,
                    modifier = Modifier.fillMaxSize())
            }
            else -> {
                // 실패 시 대체 이미지
                Icon(imageVector = Icons.Outlined.Palette, contentDescription = "fail",
                    modifier = Modifier.size(100.dp))
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