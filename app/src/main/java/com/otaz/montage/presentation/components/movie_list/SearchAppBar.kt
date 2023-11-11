package com.otaz.montage.presentation.components.movie_list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.otaz.montage.presentation.ui.movie_list.MovieListActions
import com.otaz.montage.presentation.ui.movie_list.MovieListState
import com.otaz.montage.presentation.ui.movie_list.getAllMovieCategories

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SearchAppBar(
    expression: String,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    state: MovieListState,
    onClickOpenWatchListDrawer: () -> Unit,
    actions: (MovieListActions) -> Unit,
){
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background,
        elevation = 8.dp,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 8.dp, start = 4.dp, end = 4.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Transparent,
                        disabledIndicatorColor = Transparent,
                        unfocusedIndicatorColor = Transparent,
                        backgroundColor = MaterialTheme.colors.surface,
                    ),
                    value = expression,
                    onValueChange = { userInput ->
                        actions(MovieListActions.QueryChanged(userInput))
                    },
                    placeholder = {
                        Text(text = "Search Movies")
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search,
                        ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon",
                            modifier = Modifier.clickable {
                                actions(MovieListActions.ResetForNewSearch)
                                focusRequester.requestFocus()
                            }
                        )
                    },
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            actions(MovieListActions.NewSearch)
                            focusManager.clearFocus()
                        },
                    ),
                    textStyle = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.onSurface),
                    trailingIcon = {
                        IconButton(onClick = onClickOpenWatchListDrawer) {
                            actions(MovieListActions.GetAllSavedMovies)
                            Icon(
                                imageVector = Icons.Outlined.List,
                                contentDescription = "Toggle drawer"
                            )
                        }
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 8.dp)
            ) {
                for (category in getAllMovieCategories()) {
                    MovieCategoryChip(
                        category = category.value,
                        isSelected = state.selectedCategory.value == category,
                        actions = actions,
                        focusManager = focusManager,
                    )
                }
            }
        }
    }
}