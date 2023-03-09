package com.otaz.imdbmovieapp.presentation.ui.movie_list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.otaz.imdbmovieapp.presentation.components.movie_list.MovieList
import com.otaz.imdbmovieapp.presentation.components.movie_list.SearchAppBar
import com.otaz.imdbmovieapp.presentation.theme.AppTheme
import com.otaz.imdbmovieapp.util.TAG

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieListScreen(
    onNavigateToMovieDetailScreen: (String) -> Unit,
    viewModel: MovieListViewModel,
){
    Log.d(TAG, "MovieListScreen: $viewModel")

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    // Anytime [val movies: MutableState<List<Movie>>] from [MovieListFragment] changes, this value below
    // [movies] will be updated here and in any composable that uses this value.
    val movies = viewModel.movies.value
    val configurations = viewModel.configurations.value

    val selectedCategory = viewModel.selectedCategory.value

    val loading = viewModel.loading.value

    val dialogQueue = viewModel.dialogQueue

    val page = viewModel.page.value

    // rememberScaffoldState will create a scaffold state object and persist across recompositions
    val scaffoldState = rememberScaffoldState()

    AppTheme(
        scaffoldState = scaffoldState,
        dialogQueue = dialogQueue.queue.value
    ) {
        Scaffold(
            topBar = {
                SearchAppBar(
                    expression = viewModel.query.value,
                    onQueryChanged = viewModel::onQueryChanged,
                    onExecuteSearch = { viewModel.onTriggerEvent(MovieListEvent.NewSearchEvent) },
                    focusRequester = focusRequester,
                    focusManager = focusManager,
                    resetForNextSearch = { viewModel.resetForNextSearch() },
                    categoryScrollPosition = viewModel.categoryScrollPosition,
                    selectedCategory = selectedCategory,
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
                configurations = configurations,
                onChangeMovieScrollPosition = viewModel::onChangeMovieScrollPosition,
                page = page,
                onTriggerNextPage = { viewModel.onTriggerEvent(MovieListEvent.NextPageEvent) },
                onNavigateToMovieDetailScreen = onNavigateToMovieDetailScreen,
            )
        }
    }
}