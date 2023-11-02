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
import com.otaz.montage.presentation.ui.movie_list.MovieListEvents.*
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
    movieListUIState: MovieListUIState,
    events: (MovieListEvents) -> Unit,
){
    Log.d(TAG, "MovieListScreen: $movieListViewModel")
    Log.d(TAG, "MovieListScreen: moviez ${movieListUIState.movie}")
    Log.d(TAG, "MovieListScreen: configz ${movieListUIState.configurations}")

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

//    val movies = movieListUIState.movie
//    val configurations = movieListUIState.configurations

    val savedMovies = savedMovieListViewModel.savedMovies.value
//    val configurationsUIState = movieListViewModel.configurationsState.collectAsState()
//    val configurations = movieListViewModel.configurations.value

    val selectedCategory = movieListViewModel.selectedCategory.value

    val loading = movieListViewModel.loading.value

    val page = movieListViewModel.page.value

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
                    onQueryChanged = movieListViewModel::onQueryChanged,
                    onExecuteSearch = { events(NewSearch) },
                    focusRequester = focusRequester,
                    focusManager = focusManager,
                    resetForNextSearch = { movieListViewModel.resetForNextSearch() },
                    categoryScrollPosition = movieListViewModel.categoryScrollPosition,
                    selectedCategory = selectedCategory,
                    onSelectedCategoryChanged = movieListViewModel::onSelectedCategoryChanged,
                    onChangedCategoryScrollPosition = movieListViewModel::onChangedCategoryScrollPosition,
                    onSavedMoviesIconClicked = { scope.launch { scaffoldState.drawerState.open() } }
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
                    movieListUiState = movieListUIState,
                    onChangeMovieScrollPosition = movieListViewModel::onChangeMovieScrollPosition,
                    page = page,
                    onTriggerNextPage = { events(NextPage) },
                    onNavigateToMovieDetailScreen = onNavigateToMovieDetailScreen,
                    saveMovie = { movieName ->
                        events(SaveMovie(movieName))
                    },
                )
            }
        }
    }