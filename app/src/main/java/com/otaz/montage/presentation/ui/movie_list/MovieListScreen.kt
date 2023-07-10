package com.otaz.montage.presentation.ui.movie_list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.LayoutDirection.*
import androidx.compose.ui.unit.dp
import com.otaz.montage.presentation.components.movie_list.MovieList
import com.otaz.montage.presentation.components.movie_list.SearchAppBar
import com.otaz.montage.presentation.components.saved_movies.SavedMoviesList
import com.otaz.montage.presentation.theme.AppTheme
import com.otaz.montage.presentation.ui.movie_list.MovieListEvent.*
import com.otaz.montage.presentation.ui.saved_movie_list.SavedMovieListEvent
import com.otaz.montage.presentation.ui.saved_movie_list.SavedMovieListViewModel
import com.otaz.montage.util.TAG

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieListScreen(
    onNavigateToMovieDetailScreen: (String) -> Unit,
    movieListViewModel: MovieListViewModel,
    savedMovieListViewModel: SavedMovieListViewModel,
){
    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    val movies = movieListViewModel.movies.value
    val savedMovies = savedMovieListViewModel.savedMovies.value
    val configurations = movieListViewModel.configurations.value
    val selectedCategory = movieListViewModel.selectedCategory.value
    val loading = movieListViewModel.loading.value
    val page = movieListViewModel.page.value

    val scaffoldState = rememberScaffoldState()

    AppTheme{
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
                    onExecuteSearch = { movieListViewModel.onTriggerEvent(NewSearchEvent) },
                    focusRequester = focusRequester,
                    focusManager = focusManager,
                    resetForNextSearch = { movieListViewModel.resetForNextSearch() },
                    selectedCategory = selectedCategory,
                    onSelectedCategoryChanged = movieListViewModel::onSelectedCategoryChanged,
                    onChangedCategoryScrollPosition = movieListViewModel::onChangedCategoryScrollPosition,
                    newCategorySearchEvent = { movieListViewModel.onTriggerEvent(NewCategorySearchEvent) },
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
                onTriggerNextPage = { movieListViewModel.onTriggerEvent(NextPageEvent) },
                onNavigateToMovieDetailScreen = onNavigateToMovieDetailScreen,
                saveMovie = {
                    movieListViewModel.onTriggerEvent(SaveMovieEvent(movie = it))
                },
            )
        }
    }
}