package com.otaz.imdbmovieapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
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
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .background(color = MaterialTheme.colors.surface),
//            .clickable(onClick = onClick),
    ) {
        if (loading && reviews.isEmpty()) {
            ShimmerMovieListCardItem(
                imageHeight = 250.dp,
                padding = 8.dp
            )
        } else {
            LazyRow {
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
}