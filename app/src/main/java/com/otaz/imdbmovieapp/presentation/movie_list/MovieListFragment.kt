package com.otaz.imdbmovieapp.presentation.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.materialIcon
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.otaz.imdbmovieapp.presentation.components.MovieCard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    val viewModel : MovieListViewModel by viewModels()

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                val keyboardController = LocalSoftwareKeyboardController.current

                // Anytime [val movies: MutableState<List<Movie>>] from [MovieListFragment] changes, this value below
                // [movies] will be updated here and in any composable that uses this value.
                val movies = viewModel.movies.value
                val expression = viewModel.expression.value

                Column {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = MaterialTheme.colors.primary,
                        elevation = 8.dp,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth(.9f)
                                    .padding(8.dp),
                                value = expression,
                                onValueChange = { userInput ->
                                    viewModel.onExpressionChanged(userInput)
                                },
                                label = {
                                    Text(text = "Search Movie")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Search
                                ),
                                leadingIcon = {
                                    Icon(Icons.Filled.Search, "Search Icon")
                                },
                                keyboardActions = KeyboardActions(onSearch = {
                                    viewModel.newSearch(expression)
                                    keyboardController?.hide()
                                }),
                                textStyle = TextStyle(
                                    color = MaterialTheme.colors.onSurface
                                ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = MaterialTheme.colors.surface
                                )
                            )
                        }
                    }
                    LazyColumn {
                        itemsIndexed(
                            items = movies
                        ){ index, movie ->
                            MovieCard(movie = movie, onClick = {})
                        }
                    }
                }
            }
        }
    }
}