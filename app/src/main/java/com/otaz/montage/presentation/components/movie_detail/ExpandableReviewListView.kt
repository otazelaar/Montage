package com.otaz.montage.presentation.components.movie_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.otaz.montage.domain.model.MovieReview

@Composable
fun ExpandableReviewListView(
    review: MovieReview,
){
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .width(width = 400.dp)
            .padding(horizontal = 8.dp)
            .fillMaxHeight()
            .clickable { isExpanded = !isExpanded }
    ) {
        Text(
            text = review.content,
            maxLines = if(isExpanded) Int.MAX_VALUE else 10,
            style = MaterialTheme.typography.h4,
            color = if(isExpanded) MaterialTheme.colors.onBackground else Color.DarkGray
        )
    }
}