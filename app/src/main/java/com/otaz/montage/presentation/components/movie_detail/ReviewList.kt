package com.otaz.montage.presentation.components.movie_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.otaz.montage.domain.model.MovieReview
import com.otaz.montage.presentation.components.ShimmerMovieListCardItem
import com.otaz.montage.util.MOVIE_PAGINATION_PAGE_SIZE

@Composable
fun ReviewList(
    reviews: List<MovieReview>,
    onChangeReviewScrollPosition: (Int) -> Unit,
    onTriggerNextPage: () -> Unit,
    page: Int,
){
    if (reviews.isEmpty()) {
        Text(
            text = "No reviews yet",
            style = MaterialTheme.typography.h6,
            fontStyle = FontStyle.Italic,
        )
    } else {
        LazyRow(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 4.dp)
                .width(width = 400.dp)
        ) {
            itemsIndexed(
                items = reviews
            ){ index, review ->
                onChangeReviewScrollPosition(index)
                if((index + 1) >= (page * MOVIE_PAGINATION_PAGE_SIZE)){
                    onTriggerNextPage()
                }
                ExpandableReviewListView(
                    review = review,
                )
            }
        }
    }
}