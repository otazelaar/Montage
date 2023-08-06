package com.otaz.montage.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DrawShimmerMovieCard(
    imageHeight: Dp,
    padding: Dp,
    background: Brush
) {
    Column(modifier = Modifier.padding(padding)) {
        Surface(shape = MaterialTheme.shapes.small) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .background(brush = background)
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Surface(shape = MaterialTheme.shapes.small) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight /10)
                    .background(brush = background)
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Surface(shape = MaterialTheme.shapes.small) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight /10)
                    .background(brush = background)
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Surface(shape = MaterialTheme.shapes.small) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight /10)
                    .background(brush = background)
            )
        }
    }
}