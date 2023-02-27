package com.otaz.imdbmovieapp.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
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
    onExpressionChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
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
                        onExpressionChanged(userInput)
                    },
                    label = {
                        Text(text = "Search Movies")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    leadingIcon = {
                        Icon(Icons.Filled.Search, "Search Icon")
                    },
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onExecuteSearch()
                            keyboardController?.hide()
                            focusRequester.freeFocus()
                            focusManager.clearFocus(true)
                        },
                        onDone = {
//                            KeyboardActions.Default.onDone
                            keyboardController?.hide()
                            focusRequester.freeFocus()
                            focusManager.clearFocus(true)
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
                        },
                    )
                }
            }
        }
    }
}