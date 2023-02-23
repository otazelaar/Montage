package com.otaz.imdbmovieapp.presentation.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import com.otaz.imdbmovieapp.domain.model.ImageConfigs
import com.otaz.imdbmovieapp.domain.model.OmdbMovieSpecs
import com.otaz.imdbmovieapp.domain.model.TmdbMovieSpecs
import com.otaz.imdbmovieapp.util.DEFAULT_MOVIE_IMAGE
import com.otaz.imdbmovieapp.util.loadPicture

/**
 * This screen represents the
 */

const val IMAGE_HEIGHT = 260
@Composable
fun MovieView(
    configurations: ImageConfigs,
    movieTmdb: TmdbMovieSpecs,
    movieOmdb: OmdbMovieSpecs,
){
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth(0.70f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h3
                    )
                    Text(
                        text = movieTmdb.release_date,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically), // because the rank text is smaller, it needs to be center vertically to be in line with the Title next to it.
                        style = MaterialTheme.typography.h5
                    )
                }
                movieTmdb.overview.let { plot ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        if (plot != null) {
                            Text(
                                text = plot,
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .wrapContentWidth(Alignment.Start),
                                style = MaterialTheme.typography.h3
                            )
                        }
                    }
                }
                movieOmdb.imdbRating.let { rating ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        if (rating != null) {
                            Text(
                                text = rating,
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .wrapContentWidth(Alignment.Start),
                                style = MaterialTheme.typography.h3,
                                color = Color.Green,
                            )
                        }
                    }
                }
            }
        }
    }
}