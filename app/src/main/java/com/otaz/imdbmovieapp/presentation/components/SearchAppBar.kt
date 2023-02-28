package com.otaz.imdbmovieapp.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.otaz.imdbmovieapp.presentation.ui.movie_list.MovieCategory
import com.otaz.imdbmovieapp.presentation.ui.movie_list.getAllMovieCategories
import kotlinx.coroutines.launch

/**
 * I chose to use state hoisting here to improve testability, unidirectional data flow, and reusability of Composables.
 */

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SearchAppBar(
    expression: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    resetForNextSearch: () -> Unit,
    keyboardIsVisible: Boolean,
    categoryScrollPosition: Int,
    selectedCategory: MovieCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangedCategoryScrollPosition: (Int) -> Unit,
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
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = MaterialTheme.colors.surface,
                    ),
                    value = expression,
                    onValueChange = { userInput ->
                        onQueryChanged(userInput)
                    },
                    label = {
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
                                resetForNextSearch()
                                focusRequester.requestFocus()
                            }
                        )
                    },
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onExecuteSearch()
                            focusManager.clearFocus()
                        },
                        onDone = {
                            // how do i know that this will only call once?
                            if(!keyboardIsVisible){
                                focusManager.clearFocus()
                            }
                        }
                    ),
                    textStyle = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.onSurface),
                )
            }

            val scrollState = rememberScrollState()
            val coroutineScope = rememberCoroutineScope()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 8.dp)
                    .horizontalScroll(scrollState),
            ) {
                // restore scroll position after rotation
                coroutineScope.launch {
                    scrollState.scrollTo(categoryScrollPosition)
                }

                for (category in getAllMovieCategories()) {
                    MovieCategoryChip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChanged(it) // it = category.value
                            onChangedCategoryScrollPosition(scrollState.value)
                        },
                        onExecuteSearch = {
                            onExecuteSearch()
                            focusManager.clearFocus()
                        },
                    )
                }
            }
        }
    }
}