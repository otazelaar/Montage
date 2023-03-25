package com.otaz.imdbmovieapp.presentation.components.movie_list

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.otaz.imdbmovieapp.domain.model.ImageConfigs
import com.otaz.imdbmovieapp.domain.model.Movie

@Composable
fun MovieListView(
    movie: Movie,
    configurations: ImageConfigs,
    onClick: () -> Unit,
    onMovieSaveClick: (Movie) -> Unit,
){
    Column {
        MovieListImageView(
            movie = movie,
            configurations = configurations,
            onClick = onClick,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 40.dp, start = 6.dp, end = 6.dp),
        ) {
            Text(
                text = movie.title,
                modifier = Modifier
                    .clickable(onClick = onClick)
                    .fillMaxWidth(0.90f)
                    .wrapContentWidth(Alignment.Start),
                style = MaterialTheme.typography.h4
            )
            val year = movie.release_date
            Text(
                // the dates are formatted as yyyy-dd-mm
                // All we want is the year which is the first 4 characters
                text = year.take(4),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End)
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.h5,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
        ) {
            Text(
                modifier = Modifier
                    .clickable { onMovieSaveClick(movie) },
                text = "Save",
                style = MaterialTheme.typography.h5,
                color = Color.Red
            )
        }
    }
}