package com.sooj.today_music.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DetailPageScreen(navController: NavController) {

}

@Preview
@Composable
fun DetailPagePreview() {
    val navController = rememberNavController()
    DetailPageScreen(navController)
}