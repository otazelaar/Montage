package com.otaz.montage.presentation.components.movie_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.otaz.montage.domain.model.ImageConfigs
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.presentation.components.util.DEFAULT_MOVIE_IMAGE
import com.otaz.montage.util.loadPicture

@Composable
fun MovieListImageView(
    movie: Movie,
    configurations: ImageConfigs,
    onClick: () -> Unit,
){
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        val image = movie.poster_path?.let {
            loadPicture(
                configurations = configurations,
                url = it,
                defaultImage = DEFAULT_MOVIE_IMAGE,
            ).value
        }
        image?.let { img ->
            Image(
                bitmap = img.asImageBitmap(),
                contentDescription = "movie image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(630.dp),
                contentScale = ContentScale.Crop,
            )
        }
    }
}