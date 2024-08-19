package com.sooj.today_music.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun WritePostScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
//            TextField(value = , onValueChange = )

            Button(onClick = { navController.navigate("Detail_page") }) {
                Text(text = "포스터 등록")
            }


            Button(onClick = { navController.navigate("poster_list") }) {
                Text(text = "등록")
            }
        }
    }

}

@Preview
@Composable
fun WritePostPreview() {
    val navController = rememberNavController()
    WritePostScreen(navController)
}