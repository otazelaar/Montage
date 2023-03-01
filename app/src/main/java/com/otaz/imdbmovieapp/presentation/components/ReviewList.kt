package com.otaz.imdbmovieapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.imdbmovieapp.domain.model.MovieReview
import com.otaz.imdbmovieapp.util.MOVIE_PAGINATION_PAGE_SIZE

@Composable
fun ReviewList(
    loading: Boolean,
    reviews: List<MovieReview>,
    onChangeReviewScrollPosition: (Int) -> Unit,
    onTriggerNextPage: () -> Unit,
    page: Int,
){
    if (loading && reviews.isEmpty()) {
        ShimmerMovieListCardItem(
            imageHeight = 250.dp,
            padding = 8.dp
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
                if((index + 1) >= (page * MOVIE_PAGINATION_PAGE_SIZE) && !loading){
                    onTriggerNextPage()
                }
                ExpandableReviewListView(
                    review = review,
                )
            }
        }
    }
}