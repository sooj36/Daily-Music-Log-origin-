package com.sooj.today_music.presentation

import androidx.compose.runtime.*
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Stable
fun Modifier.recomposeHighlighter(): Modifier = composed {
    var totalCompositions by remember { mutableStateOf(0L) }
    var timerJob by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()

    fun restartTimer() {
        timerJob?.cancel()
        timerJob = scope.launch {
            delay(3000)
            totalCompositions = 0
        }
    }

    totalCompositions++
    restartTimer()

    val (color, strokeWidth) = when (totalCompositions) {
        1L -> Color.Blue to 1.dp
        2L -> Color(0xFFFF214B) to 2.dp // 빨
        3L -> Color(0xFFFF9800) to 3.dp // 주
        4L -> Color(0xFFFFEB3B) to 4.dp // 노
        5L -> Color.Green to 5.dp // 초
        else -> {
            val progress = (totalCompositions - 3).coerceAtMost(100).toFloat() / 100f
            val dynamicColor = Color.Yellow.copy(alpha = 0.8f).lerpTo(
                Color.Red.copy(alpha = 0.5f),
                progress
            )
            dynamicColor to (3 + progress * 10).dp
        }
    }

    this.drawBehind {
        val strokeWidthPx = strokeWidth.toPx()
        val halfStrokeWidth = strokeWidthPx / 2
        drawRect(
            color = color,
            size = size.copy(
                width = size.width - strokeWidthPx,
                height = size.height - strokeWidthPx
            ),
            topLeft = Offset(halfStrokeWidth, halfStrokeWidth),
            style = Stroke(strokeWidthPx)
        )
    }
}

private fun Color.lerpTo(endColor: Color, fraction: Float): Color {
    return androidx.compose.ui.graphics.lerp(this, endColor, fraction)
}
