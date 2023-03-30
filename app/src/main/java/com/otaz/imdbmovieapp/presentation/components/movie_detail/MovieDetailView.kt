package com.otaz.imdbmovieapp.presentation.components.movie_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.otaz.imdbmovieapp.domain.model.ImageConfigs
import com.otaz.imdbmovieapp.domain.model.MovieReview
import com.otaz.imdbmovieapp.domain.model.OmdbMovieSpecs
import com.otaz.imdbmovieapp.domain.model.TmdbMovieSpecs
import com.otaz.imdbmovieapp.presentation.components.movie_detail.ReviewList
import com.otaz.imdbmovieapp.presentation.components.util.DEFAULT_MOVIE_IMAGE
import com.otaz.imdbmovieapp.presentation.components.util.IMAGE_HEIGHT
import com.otaz.imdbmovieapp.presentation.navigation.Screen
import com.otaz.imdbmovieapp.util.loadPicture

/**
 * This screen represents the
 */

@Composable
fun MovieDetailView(
    reviews: List<MovieReview>,
    loading: Boolean,
    onChangeReviewScrollPosition: (Int) -> Unit,
    onTriggerNextPage: () -> Unit,
    page: Int,
    configurations: ImageConfigs,
    movieTmdb: TmdbMovieSpecs,
    movieOmdb: OmdbMovieSpecs,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        movieTmdb.backdrop_path?.let { url ->
            val image = loadPicture(
                configurations = configurations,
                url = url,
                defaultImage = DEFAULT_MOVIE_IMAGE
            ).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IMAGE_HEIGHT.dp),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            movieTmdb.title.let { title ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 14.dp)
                ) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h2,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .padding(horizontal = 8.dp)
            ) {
                val director = movieOmdb.director
                Text(
                    text = "Director:   $director",
                    style = MaterialTheme.typography.h4
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .padding(horizontal = 8.dp)
            ) {
                val releaseDate = movieOmdb.released
                Text(
                    text = "Released:   $releaseDate",
                    style = MaterialTheme.typography.h4
                )
            }
            movieOmdb.runtime.let { runtime ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    if (runtime != null) {
                        Text(
                            text = "Runtime:   $runtime mins",
                            style = MaterialTheme.typography.h4,
                        )
                    }
                }
            }
            movieOmdb.imdbRating.let { imdbRating ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    if (imdbRating != null && imdbRating != "N/A") {
                        Text(
                            text = "IMDB:   $imdbRating",
                            style = MaterialTheme.typography.h4,
                            color = if (imdbRating.toDouble() >= 7.0) {
                                Color.Green
                            } else if ((imdbRating.toDouble() < 7.0) && (imdbRating.toDouble() > 5.0)) {
                                Color.Yellow
                            } else {
                                Color.Red
                            }
                        )
                    } else {
                        Text(
                            text = "IMDB:   Unreleased",
                            style = MaterialTheme.typography.h4,
                        )
                    }
                }
            }
            movieOmdb.metascore.let { metascoreRating ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .padding(horizontal = 8.dp),
                    ) {
                    if (metascoreRating != null && metascoreRating != "N/A") {
                        Text(
                            text = "Metascore:   $metascoreRating",
                            style = MaterialTheme.typography.h4,
                            color = if (metascoreRating.toInt() >= 70) {
                                Color.Green
                            } else if ((metascoreRating.toInt() < 70) && (metascoreRating.toInt() > 50)) {
                                Color.Yellow
                            } else {
                                Color.Red
                            }
                        )
                    } else {
                        Text(
                            text = "Metascore:   Unreleased",
                            style = MaterialTheme.typography.h4,
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 14.dp)
            ) {
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.Bold,
                )
            }
            movieTmdb.overview.let { plot ->
                Row(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start)
                        .width(width = 400.dp)
                        .padding(vertical = 4.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    if (plot != null) {
                        Text(
                            text = plot,
                            style = MaterialTheme.typography.h4
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 14.dp)
            ) {
                Text(
                    text = "Reviews",
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.Bold,
                )
            }
            ReviewList(
                loading = loading,
                reviews = reviews,
                onChangeReviewScrollPosition = onChangeReviewScrollPosition,
                onTriggerNextPage = onTriggerNextPage,
                page = page,
            )
        }
    }
}