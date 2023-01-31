package com.otaz.imdbmovieapp.presentation.movie_list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.otaz.imdbmovieapp.presentation.components.MovieList
import com.otaz.imdbmovieapp.presentation.components.SearchAppBar
import com.otaz.imdbmovieapp.presentation.theme.AppTheme
import com.otaz.imdbmovieapp.util.TAG

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun MovieListScreen(
    onNavigateToMovieDetailScreen: (String) -> Unit,
    viewModel: MovieListViewModel,
){
    Log.d(TAG, "MovieListScreen: ${viewModel}")
    val keyboardController = LocalSoftwareKeyboardController.current
    // Anytime [val movies: MutableState<List<Movie>>] from [MovieListFragment] changes, this value below
    // [movies] will be updated here and in any composable that uses this value.
    val movies = viewModel.movies.value
    val loading = viewModel.loading.value

    val page = viewModel.page.value

    // rememberSacffoldState will create a scaffold state object and persist across recompositions
    val scaffoldState = rememberScaffoldState()

    AppTheme(
        displayProgressBar = loading,
        scaffoldState = scaffoldState,
    ) {
        Scaffold(
            topBar = {
                SearchAppBar(
                    expression = viewModel.expression.value,
                    onExpressionChanged = viewModel::onExpressionChanged,
                    onExecuteSearch = {
                        viewModel.onTriggerEvent(MovieListEvent.NewSearchEvent)
                    },
                    keyboardController = keyboardController,
                    categoryScrollPosition = viewModel.categoryScrollPosition,
                    selectedCategory = viewModel.selectedCategory.value,
                    onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                    onChangedCategoryScrollPosition = viewModel::onChangedCategoryScrollPosition,
                )
            },
            scaffoldState = scaffoldState,
            snackbarHost = { scaffoldState.snackbarHostState },
        ){
            MovieList(
                loading = loading,
                movies = movies,
                onChangeMovieScrollPosition = viewModel::onChangeMovieScrollPosition,
                page = page,
                onTriggerNextPage = { viewModel.onTriggerEvent(MovieListEvent.NextPageEvent) },
                onNavigateToMovieDetailScreen = onNavigateToMovieDetailScreen,
            )
        }
    }
}