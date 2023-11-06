package com.otaz.montage.presentation.components.saved_movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.presentation.navigation.Screen
import com.otaz.montage.presentation.ui.movie_list.MovieListActions

@Composable
fun SavedMoviesListView(
    movieItem: Movie,
    actions: (MovieListActions) -> Unit,
    onNavigateToMovieDetailScreen: (String) -> Unit,
){
    Column{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 40.dp, start = 6.dp, end = 6.dp)
                .clickable(
                    onClick = {
                        val route = Screen.MovieDetail.route + "/${movieItem.id}"
                        onNavigateToMovieDetailScreen(route)
                    }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .wrapContentWidth(Alignment.Start)
                    .padding(end = 8.dp),
                onClick = {
                    val clickedMovieIDToBeDeleted = movieItem.id.toString()
                    actions(MovieListActions.DeleteSavedMovie(clickedMovieIDToBeDeleted))
                },
            ) {
                Text(
                    text = "Delete",
                    color = Color.Black
                )
            }
            Text(
                text = movieItem.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.Start),
                style = MaterialTheme.typography.h4
            )
        }
    }
}