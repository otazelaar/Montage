package com.otaz.montage.presentation.ui.movie_list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.LayoutDirection.*
import androidx.compose.ui.unit.dp
import com.otaz.montage.presentation.components.movie_list.MovieList
import com.otaz.montage.presentation.components.movie_list.SearchAppBar
import com.otaz.montage.presentation.components.saved_movies.SavedMoviesList
import com.otaz.montage.presentation.theme.AppTheme
import com.otaz.montage.presentation.ui.movie_list.MovieListActions.*
import com.otaz.montage.presentation.ui.saved_movie_list.SavedMovieListEvent
import com.otaz.montage.presentation.ui.saved_movie_list.SavedMovieListViewModel
import com.otaz.montage.util.TAG
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieListScreen(
    onNavigateToMovieDetailScreen: (String) -> Unit,
    movieListViewModel: MovieListViewModel,
    savedMovieListViewModel: SavedMovieListViewModel,
    movieListState: MovieListState,
    actions: (MovieListActions) -> Unit,
){
    Log.d(TAG, "MovieListScreen: $movieListViewModel")
    Log.d(TAG, "MovieListScreen: moviez ${movieListState.movie}")
    Log.d(TAG, "MovieListScreen: configz ${movieListState.configurations}")

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    val savedMovies = savedMovieListViewModel.savedMovies.value
    val loading = movieListViewModel.loading.value
//    val page = movieListViewModel.page.value

    // rememberScaffoldState will create a scaffold state object and persist across recompositions
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    AppTheme(
        scaffoldState = scaffoldState,
    ) {
        Scaffold(
            topBar = {
                SearchAppBar(
                    expression = movieListViewModel.query.value,
                    focusRequester = focusRequester,
                    focusManager = focusManager,
                    state = movieListState,
                    onSavedMoviesIconClicked = { scope.launch { scaffoldState.drawerState.open() } },
                    actions = actions,
                )
            },
            drawerContent = {
                Text("Watch List", modifier = Modifier.padding(16.dp))

                savedMovieListViewModel.onTriggerEvent(SavedMovieListEvent.UpdateSavedMoviesList)

                SavedMoviesList(
                    savedMovies = savedMovies,
                    onNavigateToMovieDetailScreen = onNavigateToMovieDetailScreen,
                    onClickDeleteMovie = {
                        savedMovieListViewModel.onTriggerEvent(SavedMovieListEvent.DeleteSavedMovie(id = it))
                    }
                )
            },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            scaffoldState = scaffoldState,
            snackbarHost = { scaffoldState.snackbarHostState },
        ){
                MovieList(
                    loading = loading,
                    onTriggerNextPage = { actions(NextPage) },
                    onNavigateToMovieDetailScreen = onNavigateToMovieDetailScreen,
                    saveMovie = { movieName ->
                        actions(SaveMovie(movieName))
                    },
                    actions = actions,
                    state = movieListState,
                )
            }
        }
    }