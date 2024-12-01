package com.sooj.today_music.presentation

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCut
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.SpeakerNotesOff
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sooj.today_music.R
import com.sooj.today_music.room.MemoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPageScreen(
    navController: NavController,
    musicViewModel: MusicViewModel = hiltViewModel(),
    memoViewModel: MemoViewModel = hiltViewModel(),
) {

    val trackClick by musicViewModel.selectedTrackEntity_st.collectAsState()

    // 선택된 트랙의 trackid로 memoentity 불러오기
    LaunchedEffect(trackClick) {
        trackClick?.let { te ->
            musicViewModel.getMmUseID_vm(te.trackId)
            Log.d("mem", "getMmUseID_vm${trackClick}")
        }
    }

    //memoentity감지
    val memoEntity by musicViewModel.memoContent_st.collectAsState()
    Log.d("sooj", "memoEntity?.trackId${memoEntity?.trackId}")
    /** 클릭한 트랙 가져오기 */
    val clickedTrack by musicViewModel.selectedTrack_st.collectAsState()

    val scrollState = rememberScrollState() // 스크롤 상태 기억

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFFEDEDE3))
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                IconButton(onClick = { navController.popBackStack() }) {
                    Image(
                        imageVector = Icons.Outlined.LibraryMusic,
                        contentDescription = "NoteList",
                        Modifier.size(28.dp)
                    )
                }

                Row {
                    if (memoEntity != null) {
                        /** 메모 삭제 */
                        IconButton(onClick = {

                            memoViewModel.deleteMemo_vm(memoEntity?.trackId ?: return@IconButton)
                        }) {
                            Image(
                                imageVector = Icons.Outlined.SpeakerNotesOff,
                                contentDescription = "delete",
                                Modifier.size(28.dp)
                            )
                        }
                    }

                    /** 트랙 삭제 */
                    IconButton(onClick = {
                        trackClick?.let { musicViewModel.deleteSavedTrack(it) }
                        navController.popBackStack()
                    }) {
                        Image(
                            imageVector = Icons.Outlined.ContentCut,
                            contentDescription = "delete",
                            Modifier.size(28.dp)
                        )
                    }
                }// icon

            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //Coil 사용
                    clickedTrack?.image?.firstOrNull()?.url?.let { DbUrl ->
                        AsyncImage(
                            model = DbUrl, contentDescription = "img",
                            modifier = Modifier
                                .size(200.dp)
                                .padding(top = 8.dp)
                        )
                    }

                    Text(
                        text = clickedTrack?.artist ?: "알 수 없 는 아티스트",
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 5.dp),
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.paperlogy_7bold)),
                        fontSize = 17.sp
                    )

                    Text(
                        text = clickedTrack?.name ?: "알 수 없 는 제목",
                        modifier = Modifier
                            .padding(top = 3.dp),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.paperlogy_8extrabold)),
                        fontSize = 23.sp
                    )
                    // 메모장
                    Card(
                        shape = RoundedCornerShape(30.dp),
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(20.dp)
                            .clickable {
                                navController.navigate("edit_detail_page")
                                memoEntity
                            }
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            memoEntity?.let { mmm ->
                                Text(
                                    text = "${mmm?.memoContent}",
                                    fontFamily = FontFamily(Font(R.font.paperlogy_4regular)),
                                    fontSize = 15.sp
                                )
                            } ?: Text(text = "새로 메모 추가하기_basic",
                                fontFamily = FontFamily(Font(R.font.paperlogy_5medium)),
                                fontSize = 17.sp,
                                modifier = Modifier.clickable {
                                    // 새로 메모 추가 로직
//                                    if (trackClick != null) {
                                    val test = trackClick!!.trackId
                                    val newMm = MemoEntity(test, memoContent = "")
                                    memoViewModel.insertMemo_vm(newMm)
                                    navController.navigate("edit_detail_page")
//                                    }
                                })
                        }
                    } // c
                } // box
            } // c
        }
    }
}