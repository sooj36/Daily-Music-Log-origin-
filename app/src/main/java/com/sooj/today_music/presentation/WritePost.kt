package com.sooj.today_music.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun WritePostScreen(navController: NavController) {

}

@Preview
@Composable
fun WritePostPreview() {
    val navController = rememberNavController()
    WritePostScreen(navController)
}