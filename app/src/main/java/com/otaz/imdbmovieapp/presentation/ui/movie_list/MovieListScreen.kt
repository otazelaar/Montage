package com.otaz.imdbmovieapp.presentation.ui.movie_list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.otaz.imdbmovieapp.presentation.components.saved_movies.SavedMoviesList
import com.otaz.imdbmovieapp.presentation.components.movie_list.MovieList
import com.otaz.imdbmovieapp.presentation.components.movie_list.SearchAppBar
import com.otaz.imdbmovieapp.presentation.theme.AppTheme
import com.otaz.imdbmovieapp.presentation.ui.saved_movie_list.SavedMovieListEvent
import com.otaz.imdbmovieapp.presentation.ui.saved_movie_list.SavedMovieListViewModel
import com.otaz.imdbmovieapp.util.TAG

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieListScreen(
    onNavigateToMovieDetailScreen: (String) -> Unit,
    movieListViewModel: MovieListViewModel,
    savedMovieListViewModel: SavedMovieListViewModel,
){
    Log.d(TAG, "MovieListScreen: $movieListViewModel")

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    // Anytime [val movies: MutableState<List<Movie>>] from [MovieListFragment] changes, this value below
    // [movies] will be updated here and in any composable that uses this value.
    val movies = movieListViewModel.movies.value
    val savedMovies = savedMovieListViewModel.savedMovies.value
    val configurations = movieListViewModel.configurations.value

    val selectedCategory = movieListViewModel.selectedCategory.value

    val loading = movieListViewModel.loading.value

    val dialogQueue = movieListViewModel.dialogQueue

    val page = movieListViewModel.page.value

    // rememberScaffoldState will create a scaffold state object and persist across recompositions
    val scaffoldState = rememberScaffoldState()

    AppTheme(
        scaffoldState = scaffoldState,
        dialogQueue = dialogQueue.queue.value,
    ) {
        Scaffold(
            drawerContent = {
                Text("Watch List", modifier = Modifier.padding(16.dp))
                if (scaffoldState.drawerState.isOpen){
                    savedMovieListViewModel.onTriggerEvent(SavedMovieListEvent.UpdateSavedMoviesList)

                    SavedMoviesList(
                        savedMovies = savedMovies,
                        onNavigateToMovieDetailScreen = onNavigateToMovieDetailScreen,
                        onClickDeleteMovie = {
                            savedMovieListViewModel.onTriggerEvent(SavedMovieListEvent.DeleteSavedMovie(id = it))
                        }
                    )
                }
            },
            drawerGesturesEnabled = true,
            topBar = {
                SearchAppBar(
                    expression = movieListViewModel.query.value,
                    onQueryChanged = movieListViewModel::onQueryChanged,
                    onExecuteSearch = { movieListViewModel.onTriggerEvent(MovieListEvent.NewSearchEvent) },
                    focusRequester = focusRequester,
                    focusManager = focusManager,
                    resetForNextSearch = { movieListViewModel.resetForNextSearch() },
                    categoryScrollPosition = movieListViewModel.categoryScrollPosition,
                    selectedCategory = selectedCategory,
                    onSelectedCategoryChanged = movieListViewModel::onSelectedCategoryChanged,
                    onChangedCategoryScrollPosition = movieListViewModel::onChangedCategoryScrollPosition,
                )
            },
            scaffoldState = scaffoldState,
            snackbarHost = { scaffoldState.snackbarHostState },
        ){
            MovieList(
                loading = loading,
                movies = movies,
                configurations = configurations,
                onChangeMovieScrollPosition = movieListViewModel::onChangeMovieScrollPosition,
                page = page,
                onTriggerNextPage = { movieListViewModel.onTriggerEvent(MovieListEvent.NextPageEvent) },
                onNavigateToMovieDetailScreen = onNavigateToMovieDetailScreen,
                saveMovie = {
                    movieListViewModel.onTriggerEvent(MovieListEvent.SaveMovieEvent(movie = it))
                },
            )
        }
    }
}