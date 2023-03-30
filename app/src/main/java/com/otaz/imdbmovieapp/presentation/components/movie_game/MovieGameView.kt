package com.otaz.imdbmovieapp.presentation.components.movie_game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.otaz.imdbmovieapp.domain.model.Movie

@Composable
fun MovieGameView(
    movie: Movie,
){
    Column {
        Row {
            // the dates are formatted as yyyy-dd-mm
            // All we want is the year which is the first 4 characters
            val year = movie.release_date.take(4)
            Text(
                text = "${movie.title} ($year)",
                style = MaterialTheme.typography.h4,
            )
        }
    }
}