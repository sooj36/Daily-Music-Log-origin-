package com.sooj.today_music.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sooj.today_music.R

@Composable
fun DetailPageScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(text = "TITLE", modifier = Modifier.fillMaxWidth(), fontSize = 15.sp)
            Image(painterResource(id = R.drawable.ic_launcher_foreground) , contentDescription = null)
            Text(text = "2024-08-19")

        }
    }

}

@Preview
@Composable
fun DetailPagePreview() {
    val navController = rememberNavController()
    DetailPageScreen(navController)
}