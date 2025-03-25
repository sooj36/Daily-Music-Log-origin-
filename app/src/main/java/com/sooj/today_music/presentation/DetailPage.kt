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
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.SpeakerNotesOff
import androidx.compose.material.icons.sharp.DeleteOutline
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sooj.today_music.R
import com.sooj.today_music.domain.Track
import com.sooj.today_music.room.MemoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPageScreen(
    navController: NavController,
    musicViewModel: MusicViewModel = hiltViewModel(),
    memoViewModel: MemoViewModel = hiltViewModel(),
) {
    Log.d(" soj", "1. ===== 전체 화면 재구성 시작 =====") //
    //
    val trackClick by musicViewModel.selectedTrackEntity_st.collectAsState().also {
        Log.d("soj", "2 trackClick: ${it.value?.trackId}")
    }

    //memoentity 감지
    val memoEntity by musicViewModel.memoContent_st.collectAsState().also {
        Log.d("soj", "3 memoEntity: ${it?.value?.trackId}") // ✅
        Log.d("soj", "3 memoEntity: ${it?.value?.memoContent}") // ✅
    }


    /** 클릭한 트랙 가져오기 */
    val clickedTrack by musicViewModel.selectedTrack_st.collectAsState().also {
        Log.d("soj", "4 clickedTrack: ${it?.value?.name}") // ✅
    }

    // ref - 상태 통합
    data class DetailState(
        val track : Track? = null,
        val memo : MemoEntity? = null,
        val loading : Boolean = false
    )

//    val detailState by viewModel.detailState.collectAsState()


    val scrollState = rememberScrollState() // 스크롤 상태 기억

    LaunchedEffect(trackClick) {
        trackClick?.let { te ->
            // 상태 변경 트리거 // 내부에서 selectedTrack_st 변경.
            musicViewModel.getMmUseID_vm(te.trackId)
            Log.d("mem", "선택된 트랙 ID를 MEM엔티티로 ${trackClick}")
        }
    }

    // 이벤트 핸들러 정의
    val handleNavigateToEdit = {
        navController.navigate("edit_detail_page")
    }

    val handleAddNewMemo = {
        trackClick?.let { track ->
            val newMm = MemoEntity(track.trackId, memoContent = "new mm")
            memoViewModel.insertMemo_vm(newMm)
            navController.navigate("edit_detail_page")
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White)
//            .background(Color(0xFFEDEDE3))
    ) {
        Column {


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
                        } ?: Text(text = "메 모 추 가 N E W",
                            fontFamily = FontFamily(Font(R.font.paperlogy_5medium)),
                            fontSize = 17.sp,
                            modifier = Modifier.clickable {
//                                     새로 메모 추가 로직
                                trackClick?.let { new ->
                                    val test = new.trackId
                                    val newMm = MemoEntity(test, memoContent = "new Mm")
                                    memoViewModel.insertMemo_vm(newMm)
                                    navController.navigate("edit_detail_page")
                                }
                            })
                    }
                }
            }
        }
    }
}


@Composable
private fun Topbar(
    onBackClick: () -> Unit,
    onDeleteMemo: () -> Unit,
    onDeleteTrack: () -> Unit,
    showDeleteMemo: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        IconButton(onClick = onBackClick) {
            Image(
                imageVector = Icons.Outlined.LibraryMusic,
                contentDescription = "NoteList",
                Modifier.size(28.dp)
            )
        }

        Row {
            if (showDeleteMemo) {
                /** 메모 삭제 */
                IconButton(onClick = onDeleteMemo)
                {
                    Image(
                        imageVector = Icons.Outlined.SpeakerNotesOff,
                        contentDescription = "delete",
                        Modifier.size(28.dp)
                    )
                }
            } // 메모 삭제

            /** 트랙 삭제 */
            IconButton(onClick = onDeleteTrack) {
                Image(
                    imageVector = Icons.Sharp.DeleteOutline,
                    contentDescription = "delete",
                    Modifier.size(28.dp)
                )
            }
        }// icon
    }
}