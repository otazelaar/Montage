package com.otaz.imdbmovieapp.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.imdbmovieapp.domain.model.MovieReview

@Composable
fun ExpandableReviewListView(
    review: MovieReview,
){
    var expandableState by remember { mutableStateOf(false) }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(8.dp)
            .width(width = 400.dp)
            .fillMaxHeight()
            .animateContentSize(
                animationSpec = tween(
                    delayMillis = 300,
                    easing = LinearOutSlowInEasing,
                ),
            )
    ) {
        Column(
            modifier = Modifier
            .padding(8.dp)
        ) {
            Row {
                Text(
                    text = review.content,
                    style = MaterialTheme.typography.h4
                )
            }
        }
    }
}