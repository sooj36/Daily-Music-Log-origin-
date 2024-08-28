package com.sooj.today_music.presentation

import android.text.style.BackgroundColorSpan
import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.sooj.today_music.R

@Composable
fun DetailPageScreen(navController: NavController, musicViewModel: SearchViewModel) {
    /** 클릭한 트랙 가져오기 */
    val clickedTrack by musicViewModel.selectedTrack
    Log.d("클릭한 트랙 가져오기", "클릭 정보 : ${clickedTrack}")
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Column {
            Row {
                Spacer(modifier = Modifier.height(16.dp))
                IconButton(onClick = { navController.popBackStack() }) {
                    Image(imageVector = Icons.Default.ArrowBackIos, contentDescription = "back")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            Box(modifier = Modifier) {
                Column(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = clickedTrack?.image?.find { it.size == "large" }?.url,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = clickedTrack?.artist ?: "알 수 없 는 아티스트",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = clickedTrack?.name ?: "알 수 없 는 제목",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Hello, hello\n" +
                                "I'm not where I'm supposed to be\n" +
                                "I hope that you're missin' me\n" +
                                "'Cause it makes me feel young\n" +
                                "Hello, hello\n" +
                                "Last time that I saw your face\n" +
                                "Was recess in second grade\n" +
                                "And it made me feel young\n" +
                                "Won't you help me sober up?\n" +

                                "Growin' up, it made me numb\n" +

                                "And I wanna feel somethin' again\n" +

                                "Won't you help me sober up?\n" +

                                "All the big kids, they got drunk\n" +

                                "And I want to feel somethin' again (Oh, oh, oh, oh)\n" +

                                "Won't you help me feel somethin' again?\n" +

                                "How's it go again?\n" +
                                "이 노래에서는 상대가 긍정적으로 동참하도록 유도하는 질문입니다.\n" +
                                "\"Goodbye, goodbye\"\n" +
                                "\n" +
                                "I said to my best-est buds\n" +
                                "\n" +
                                "We said that we'd keep in touch\n" +
                                "\n" +
                                "And we did our best\n" +
                                "\n" +
                                "All my new friends\n" +
                                "\n" +
                                "We smile at party time\n" +
                                "\n" +
                                "But soon we forget to smile\n" +
                                "\n" +
                                "At anything else\n" +
                                "Won't you help me sober up?\n" +
                                "\n" +
                                "Growin' up, it made me numb\n" +
                                "\n" +
                                "And I want to feel somethin' again\n" +
                                "\n" +
                                "Won't you help me sober up?\n" +
                                "\n" +
                                "All the big kids, they got drunk\n" +
                                "\n" +
                                "And I want to feel somethin' again (Oh, oh, oh, oh)\n" +
                                "\n" +
                                "Won't you help me feel somethin' again?\n" +
                                "\n" +
                                "How's it go again?\n" +
                                "\n" +
                                "(반복)\n" +
                                "\n" +
                                "\u200B\n" +
                                "\n" +
                                "How's it go again? (Go again, go again, go again…)\n" +
                                "\n" +
                                "\u200B\n" +
                                "\n" +
                                "나랑 함께 하는 거 어때?\n" +
                                "\n" +
                                "\u200B\n" +
                                "\n" +
                                "My favorite color is you\n" +
                                "\n" +
                                "You're vibratin' out my frequency\n" +
                                "\n" +
                                "My favorite color is you\n" +
                                "\n" +
                                "You keep me young and that's how I wanna be\n" +
                                "\n" +
                                "My favorite color is you\n" +
                                "\n" +
                                "You're vibratin' out my frequency\n" +
                                "\n" +
                                "My favorite color is you\n" +
                                "\n" +
                                "You keep me young and that's how I wanna be\n" +
                                "\n" +
                                "\u200B\n" +
                                "\n" +
                                "내가 가장 좋아하는 색은 너야.\n" +
                                "\n" +
                                "넌 내 주파수에 에너지를 주거든.\n" +
                                "\n" +
                                "내가 가장 좋아하는 색은 너야.\n" +
                                "\n" +
                                "넌 내가 원하는대로 젊게 유지해 주니까.\n" +
                                "\n" +
                                "내가 가장 좋아하는 색은 너야.\n" +
                                "\n" +
                                "넌 내 주파수에 에너지를 주거든.\n" +
                                "\n" +
                                "내가 가장 좋아하는 색은 너야.\n" +
                                "\n" +
                                "넌 내가 원하는대로 젊게 유지해 주니까.\n" +
                                "\n" +
                                "\u200B\n" +
                                "\n" +
                                "You're vibratin' out my frequency~설명:\n" +
                                "\n" +
                                "구글에 의하면 인간은 서로 다른 주파수로 진동하며 각각의 진동은 느낌이며 에너지 파동을 생성한다고 합니다.\n" +
                                "\n" +
                                "이런 관점에서, 상대방이 내 주파수에 에너지를 넘치게, 반응하게 한다고 봅니다.\n" +
                                "\n" +
                                "\u200B\n" +
                                "\n" +
                                "Hello, hello\n" +
                                "\n" +
                                "I'm not where I'm supposed to be\n" +
                                "\n" +
                                "I hope that you're missin' me\n" +
                                "\n" +
                                "'Cause it makes me feel young\n" +
                                "\n" +
                                "Hello, hello\n" +
                                "\n" +
                                "Last time that I saw your face\n" +
                                "\n" +
                                "Was recess in second grade\n" +
                                "\n" +
                                "And it made me feel young\n" +
                                "\n" +
                                "\u200B\n" +
                                "\n" +
                                "안녕, 안녕!\n" +
                                "\n" +
                                "여긴 내가 꿈꾸던 곳이 아닌것 같아.\n" +
                                "\n" +
                                "너도 날 그리워하면 좋겠어.\n" +
                                "\n" +
                                "난 어릴적 기분으로 돌아간 것 같거든.\n" +
                                "\n" +
                                "안녕, 안녕! \n" +
                                "\n" +
                                "마지막으로 네 얼굴을 본 건\n" +
                                "\n" +
                                "2학년 때, 쉬는 시간이었어\n" +
                                "\n" +
                                "난 어릴적으로 돌아간 기분이야.\n" +
                                "\n" +
                                "\u200B\n" +
                                "\n" +
                                "And I want to feel somethin' again\n" +
                                "\n" +
                                "I just wanna feel somethin' again\n" +
                                "\n" +
                                "How's it go again?\n" +
                                "\n" +
                                "\u200B\n" +
                                "\n" +
                                "난 다시 뭔가를 느끼고 싶어\n" +
                                "\n" +
                                "난 다시 뭔가를 느끼고 싶어.\n" +
                                "\n" +
                                "나랑 함께 하는 거 어때?\n" +
                                "\n" +
                                "\u200B\n" +
                                "\n" +
                                "Won't you help me sober up?\n" +
                                "\n" +
                                "Growin' up, it made me numb\n" +
                                "\n" +
                                "And I want to feel somethin' again (My favorite color is you)\n" +
                                "\n" +
                                "Won't you help me sober up?\n" +
                                "\n" +
                                "All the big kids, they got drunk\n" +
                                "\n" +
                                "And I want to feel somethin' again (My favorite color is you)\n" +
                                "\n" +
                                "Won't you help me feel somethin' again? (My favorite color is you)\n" +
                                "\n" +
                                "Can I finally feel somethin' again?\n" +
                                "\n" +
                                "How's it go again?\n"
                    )
                }
            } // box
            IconButton(onClick = { navController.navigate("edit_detail_page") }) {
                Image(imageVector = Icons.Default.Edit, contentDescription = "edit")
            } //column
        }
    }
}

@Preview
@Composable
fun DetailPagePreview() {
    val navController = rememberNavController()
    DetailPageScreen(navController, hiltViewModel())
}