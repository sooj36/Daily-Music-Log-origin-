package com.sooj.today_music.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AlarmOn
import androidx.compose.material.icons.outlined.DataSaverOn
import androidx.compose.material.icons.outlined.MusicVideo
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.decode.ImageSource

@Composable
fun AlarmPageScreen(navController: NavController) {

    IconButton(onClick = {
        // 알림 저장
    }) {
        Image(
            imageVector = Icons.Outlined.AlarmOn,
            contentDescription = "NoteList",
            Modifier.size(28.dp)
        )
    }
    Text(text = "alarm_test")
}


@Preview
@Composable
fun AlarmPagePreview() {
    val navController = rememberNavController()
    SearchPageScreen(navController, hiltViewModel())
}