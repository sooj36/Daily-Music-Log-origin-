package com.sooj.today_music.presentation

import android.graphics.ImageDecoder.ImageInfo
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
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sooj.today_music.BuildConfig
import com.sooj.today_music.R
import com.sooj.today_music.domain.MusicInfoModel_dc
import okhttp3.Request
import okhttp3.OkHttpClient
import okhttp3.Call
import okhttp3.Response
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException


@Composable
fun SearchPageScreen(navController: NavController, musicViewModel: SearchViewModel) {
    val searchList by musicViewModel.searchList

    /** val infoList by musicViewModel.infoList */
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            IconButton(onClick = { navController.popBackStack() }) {
                Image(imageVector = Icons.Default.ArrowBackIos, contentDescription = "back")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
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
                                    text = "오늘의 노래를 검색하세요(띄어쓰기 유의)",
                                    style = TextStyle(color = Color.Gray)
                                )
                            } else {
                                // 음악 검색
//                                viewModel.getMusic(track)
                            }
                            innerTextField() // 실제 텍스트 입력 필드
                        }
                    } // decorationBox
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = { musicViewModel.getMusic(text) }) {
                    Image(imageVector = Icons.Default.Search, contentDescription = "search")

                }
            } // row
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxSize()
                    .background(Color.LightGray),

                ) {
                items(searchList.size) { index ->
                    val track = searchList[index]
                    val trackName = track.name ?: return@items
                    val artistName = track.artist ?: return@items

                    /////////////////////////////
                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                            .clickable {
                                // 클릭 시, Viewmodel에 선택된 트랙 저장
                                musicViewModel.selectTrack(track)
                                Log.d(
                                    "VIEWMODEL에 선택 트랙 저장",
                                    "저장된 ${musicViewModel.selectedTrack.value}"
                                )

                                // 다른 페이지로 이동
                                navController.navigate("poster_list")
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        /** 앨범 이미지 */
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(track.image?.find { it.size == "extralarge" }?.url).build(),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        /** 트랙명 */
                        Text(
                            text = track.name.toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        /** 아티스트명 */
                        Text(
                            text = track.artist.toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } // index
            }
        }
    }
}

fun ImageInfo(trackName: String, artistName: String): MusicInfoModel_dc? {
    // API URL 생성
    val url = "http://ws.audioscrobbler.com/2.0/?method=track.getInfo" +
            "&api_key=${BuildConfig.LAST_FM_API_KEY}" +
            "&artist=${artistName}" +
            "&track=${trackName}" +
            "&format=json"

    val client = OkHttpClient()
    val gson = Gson()

    // OKHTTP 이용한 API 요청
    val request = Request.Builder()
        .url(url)
        .build()

    // API 호출 및 응답 처리
    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) {
            return null
        }

        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, MusicInfoModel_dc::class.java)
    }
}

@Preview
@Composable
fun WritePostPreview() {
    val navController = rememberNavController()
    SearchPageScreen(navController, hiltViewModel())
}