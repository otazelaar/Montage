package com.otaz.montage.presentation.components.movie_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.presentation.ui.movie_list.MovieListActions
import com.otaz.montage.presentation.ui.movie_list.MovieListState

@Composable
fun MovieListView(
    movieItem: Movie,
    onClick: () -> Unit,
    state: MovieListState,
    actions: (MovieListActions) -> Unit,
){
    Column {
        MovieListImageView(
            movieItem = movieItem,
            onClick = onClick,
            state = state,
        )
        Row(
            Modifier
                .fillMaxHeight()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(.80f)
            ) {
                // the dates are formatted as yyyy-dd-mm
                // All we want is the year which is the first 4 characters
                val year = movieItem.release_date?.take(4)
                Text(
                    text = "${movieItem.title} ($year)",
                    modifier = Modifier
                        .clickable(onClick = onClick),
                    style = MaterialTheme.typography.h4,
                )
            }
            Row{
                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()

//                 Make animation of clicking this button.
//                 Make button look different once saved and store this in DB so that every time
//                 the app opens, a movie that is in your watchlist is reflected by a
//                 checkmark and a change of color on the button below.
//                 The isPressed way of doing things only knows when the button is actively being
//                 pressed.

                Button(
                    onClick = {
//                        actions(MovieListActions.SaveMovieAction(movieItem))
                        actions(MovieListActions.SaveMovieToWatchlist(movieItem))
                    },
                    interactionSource = interactionSource,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isPressed){
                            Color.Red
                        }else{
                            MaterialTheme.colors.primary
                        }
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Add movie to watch list",
                        tint = if (isPressed){
                            Color.Red
                        }else{
                            Color.Black
                        }
                    )
                }
            }
        }
    }
}