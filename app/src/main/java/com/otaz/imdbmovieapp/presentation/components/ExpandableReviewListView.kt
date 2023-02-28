package com.otaz.imdbmovieapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.imdbmovieapp.domain.model.MovieReview

@Composable
fun ExpandableReviewListView(
    review: MovieReview,
){
    var expandableState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .width(width = 400.dp)
            .padding(horizontal = 8.dp)
            .fillMaxHeight()
    ) {
        Row {
            Text(
                text = review.content,
                style = MaterialTheme.typography.h4,
            )
        }
    }
}