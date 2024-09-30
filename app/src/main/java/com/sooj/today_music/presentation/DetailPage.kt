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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sooj.today_music.room.MemoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPageScreen(
    navController: NavController,
    musicViewModel: MusicViewModel = hiltViewModel(),
    memoViewModel: MemoViewModel = hiltViewModel(),
    trackId : Int // 네비게이션 경로로 전달받은 trackid
    ) {
    /** 클릭한 트랙 가져오기 */
    val clickedTrack by musicViewModel.selectedTrack
    Log.d("DetailPageScreen", "Clicked track: $clickedTrack")

    /** vm 의 Flow 데이터를 State로 변환 */
    val memoState by memoViewModel.memoContent.collectAsState()

    val getImageUrl by musicViewModel.getAlbumImage
    val imgUrl = remember { getImageUrl }

    // ViewModel에서 메모 데이터를 가져와서 UI에 반영
    val memo by memoViewModel.memoContent.collectAsState()

    // textfield에서 사용자가 입력한 메모 저장할 상태
//    var memoContent by remember { mutableStateOf(memo?.memoContent ?: "") }

    val scrollState = rememberScrollState() // 스크롤 상태 기억

    // memo 데이터를 trackId로 로드
    LaunchedEffect(trackId) {
        memoViewModel.loadMemoForTrack(trackId) // 트랙에 해당하는 메모
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                IconButton(onClick = { navController.popBackStack() }) {
                    Image(imageVector = Icons.Default.LibraryMusic, contentDescription = "NoteList")
                }

                IconButton(onClick = {
//                    // 이미 동일한 페이지에 있을 때 다시 네비게이션 되지 않게
//                    if (navController.currentDestination?.route != "edit_detail_page") {
                        navController.navigate("edit_detail_page")
//                    }

                }) {
                    Image(imageVector = Icons.Default.StickyNote2, contentDescription = "edit")
                }
            }
            Box(modifier = Modifier) {
                Column(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .fillMaxWidth()
                        .background(Color.Yellow)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (imgUrl != null) {
//                        Log.d("detail_image", "image_URL: ${imgUrl}")
                        AsyncImage(
                            model = imgUrl,
                            contentDescription = "image",
                            modifier = Modifier.size(200.dp)
                        )
                    } else {
//                        Image(painterResource(id = R.drawable.img), contentDescription = "error")
                    }
                    //Coil 사용
                    clickedTrack?.image?.firstOrNull()?.url?.let { DbUrl ->
                        AsyncImage(
                            model = DbUrl, contentDescription = "img",
                            modifier = Modifier.size(200.dp)
                        )

                    }
                    Text(
                        text = clickedTrack?.artist ?: "알 수 없 는 아티스트",
                        modifier = Modifier
                            .fillMaxSize()
                            .size(30.dp)
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = clickedTrack?.name ?: "알 수 없 는 제목",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(27.dp)
                            .padding(start = 8.dp)
                            .background(Color.Gray),
                        fontWeight = FontWeight.SemiBold

                    )
                    Card(
                        shape = RoundedCornerShape(30.dp),
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(20.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(20.dp),
                            text = "// [Verse 1]\n" +
                                    "How could my day be bad    \n" +
                                    "when I'm with you?         \n" +
                                    "You're the only one who makes me laugh  \n" +
                                    "So how can my day be bad?  \n" +
                                    "It's a day for you          \n" +
                                    "\n" +
                                    "// [Verse 2]\n" +
                                    "Lately, life's so boring    \n" +
                                    "I've been watching Netflix all day long  \n" +
                                    "I thought there would be    \n" +
                                    "no things left to watch     \n" +
                                    "so I let myself out         \n" +
                                    "\n" +
                                    "// [Verse 3]\n" +
                                    "When I went to the park     \n" +
                                    "I recognised you at a glance  \n" +
                                    "Face to face, we just smiled  \n" +
                                    "We already know that we'll be together  \n" +
                                    "\n" +
                                    "// [Chorus]\n" +
                                    "How could my day be bad when I'm with you?  \n" +
                                    "You're the only one who makes me laugh     \n" +
                                    "So how can my day be bad?  \n" +
                                    "It's a day for you          \n" +
                                    "\n" +
                                    "// [Verse 4]\n" +
                                    "Coffee in the morning, you and the sun  \n" +
                                    "There's a brown hue in your eyes  \n" +
                                    "How pretty it is            \n" +
                                    "I think I'm in love          \n" +
                                    "\n" +
                                    "// [Verse 5]\n" +
                                    "When I went to the park     \n" +
                                    "I recognised you at a glance  \n" +
                                    "Face to face, we smiled     \n" +
                                    "and I finally held your hands  \n" +
                                    "\n" +
                                    "// [Chorus]\n" +
                                    "How could my day be bad when I'm with you?  \n" +
                                    "You're the only one who makes me laugh  \n" +
                                    "So how can my day be bad?  \n" +
                                    "It's a day for you"

                        )
                        TextField(
                            value = memoState?.memoContent ?: "",
                            onValueChange = {newText ->
                                memoViewModel.saveMemo_vm(trackId, newText)
                            },
                            label = { Text(text = "enter memo")})
                    } // card text
                } // c2
            } // box
        } // c
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMemo(
    memoViewModel: MemoViewModel,
    memoEntity: MemoEntity // 메모 기본 데이터
) {
    var editing by remember { mutableStateOf(false) }
    var updatedMemo by remember { mutableStateOf(memoEntity.memoContent) }

    Column {
        if (editing) {
            // 수정 상태면 텍스트필드
            TextField(
                value = updatedMemo,
                onValueChange = { updatedMemo = it }, // 수정 저장
            )
            Button(onClick = { /*저장 버튼 클릭 시 텍스트 저장되게*/ }) {
                memoViewModel.saveMemo_vm(memoEntity.trackId, updatedMemo) // 메모 저장
                editing = false // 저장 후 읽기모드로 전환
            }
            Text(text = "saveeeee")
        } else {
            // 수정 전일 때는 텍스트 표시
            Text(
                text = if (memoEntity.memoContent.isNullOrBlank()) {
                    "오늘의 음악을 기록하세요 !" // 메모 없을 시
                } else {
                    memoEntity.memoContent // 해당 메모 표시

                }, modifier = Modifier.fillMaxWidth()
            )
        }
    }
}