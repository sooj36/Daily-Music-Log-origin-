package com.sooj.today_music.presentation

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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.sooj.today_music.R

@Composable
fun SearchPageScreen(viewModel: SearchViewModel) {


    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Image(painterResource(id = R.drawable.back), contentDescription = "back",
                modifier = Modifier
                    .clickable {
//                        navController.popBackStack()
                    }
                    .size(24.dp))
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {
//                    navController.navigate(Screen.PosterList.route)
                }) {
                    Text(text = "추가")
                }
            }


            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var track by remember {
                    mutableStateOf("")
                }
                BasicTextField(modifier = Modifier
                    .width(80.dp)
                    .weight(8f)
                    .border(color = Color.Transparent, width = 1.dp)
                    .background(Color.LightGray),
                    value = track, onValueChange = { track = it },
                    textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                        ) {
                            if (track.isEmpty()) {
                                Text(
                                    text = "오늘의 노래를 검색하세요", style = TextStyle(color = Color.Gray)
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

                Button(onClick = {
                    viewModel.getMusic(track)
                }) {
                    Modifier
                        .weight(2f)
                        .background(Color.Black)
                    Text(text = "검색")
                    // 검색 api 진입 버튼

                }
            } // row
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                modifier = Modifier.padding(9.dp)
                    .fillMaxSize()
                    .background(Color.Transparent),
                content =  { }) // https://www.youtube.com/watch?v=pzSWTNK9h7M&t=1860s
        }
    }
}

@Preview
@Composable
fun WritePostPreview() {
    val navController = rememberNavController()
//    SearchPageScreen(navController)
}