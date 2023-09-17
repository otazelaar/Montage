package com.otaz.montage.presentation.components.movie_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.otaz.montage.domain.model.ImageConfigs
import com.otaz.montage.domain.model.Movie

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
                val year = movie.release_date?.take(4)
                Text(
                    text = "${movie.title} ($year)",
                    modifier = Modifier
                        .clickable(onClick = onClick),
                    style = MaterialTheme.typography.h4,
                )
            }
            Row{
                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()
                Button(
                    onClick = { onMovieSaveClick(movie) },
                    interactionSource = interactionSource,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
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
//                    Text(
//                        text = "Save",
//                        style = MaterialTheme.typography.h5,
//                        color = if (isPressed){
//                            Color.Red
//                        }else{
//                            Color.Black
//                        }
//                    )
                }
            }
        }
    }
}