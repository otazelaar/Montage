package com.otaz.imdbmovieapp.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerMovieCardItem(
    imageHeight: Dp,
    padding: Dp = 16.dp
){
    val colors = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.4f),
        Color.LightGray.copy(alpha = 0.9f)
    )
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val widthPx = with(LocalDensity.current) { (maxWidth - padding * 2f).toPx() }
        val heightPx = with(LocalDensity.current) { (imageHeight - padding).toPx() }
        val gradientWidthPx = 0.3f * heightPx

        val shimmerAnimationSpec = infiniteRepeatable<Float>(
            animation = tween(
                durationMillis = 900,
                delayMillis = 300,
                easing = LinearEasing
            )
        )
        val infiniteTransition = rememberInfiniteTransition()

        val xPosition = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = widthPx,
            animationSpec = shimmerAnimationSpec
        )
        val yPosition = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = heightPx,
            animationSpec = shimmerAnimationSpec
        )
        val brush = Brush.linearGradient(
            colors = colors,
            start = Offset(
                x = xPosition.value - gradientWidthPx,
                y = yPosition.value - gradientWidthPx
            ),
            end = Offset(x = xPosition.value, y = yPosition.value)
        )

        Column {
            DrawShimmerMovieCard(
                imageHeight = imageHeight,
                padding = padding,
                background = brush
            )
        }
    }
}