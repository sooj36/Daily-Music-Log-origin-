package com.sooj.today_music.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun PosterListScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {

            Button(onClick = { navController.navigate("write_post") }) {
                Text(text = "포스팅")
            }

            Text(text = " P O S T E R  L I S T S C R E E N ")
            Spacer(modifier = Modifier.height(25.dp))

            Text(text = " P O S T E R  L I S T S C R E E N ")
            Spacer(modifier = Modifier.height(25.dp))

            Text(text = " P O S T E R  L I S T S C R E E N ")
            Spacer(modifier = Modifier.height(25.dp))

            Text(text = " P O S T E R  L I S T S C R E E N ")
            Spacer(modifier = Modifier.height(25.dp))

            Text(text = " P O S T E R  L I S T S C R E E N ")
            Spacer(modifier = Modifier.height(25.dp))

            Text(text = " P O S T E R  L I S T S C R E E N ")
            Spacer(modifier = Modifier.height(25.dp))

            Text(text = " P O S T E R  L I S T S C R E E N ")
            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@Preview
@Composable
fun PosterListPreview() {
    val navController = rememberNavController()
    PosterListScreen(navController)
}