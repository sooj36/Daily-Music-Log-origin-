package com.sooj.today_music.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sooj.today_music.R

@Composable
fun WritePostScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Image(painterResource(id = R.drawable.back), contentDescription = "back",
                modifier = Modifier.clickable { navController.popBackStack() })

            Row {
                var text by remember { mutableStateOf("enter the text here") }

                BasicTextField(modifier = Modifier
                    .width(50.dp)
                    .border(color = Color.Red, width = 1.dp)
                    .background(Color.LightGray),
                    value = text , onValueChange = { text = it },
                    textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box {
                            //
                        }
                    }
                )

                Button(onClick = { /*TODO*/ }) {
                    // 검색 api 진입 버튼
                    
                }
            }
//

        }
    }

}

@Preview
@Composable
fun WritePostPreview() {
    val navController = rememberNavController()
    WritePostScreen(navController)
}