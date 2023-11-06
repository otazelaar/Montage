package com.otaz.montage.presentation.ui.movie_list

import android.annotation.SuppressLint
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
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieListScreen(
    onNavigateToMovieDetailScreen: (String) -> Unit,
    movieListViewModel: MovieListViewModel,
    state: MovieListState,
    actions: (MovieListActions) -> Unit,
) {
    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
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
                    state = state,
                    onClickOpenWatchListDrawer = { scope.launch { scaffoldState.drawerState.open() } },
                    actions = actions,
                )
            },
            drawerContent = {
                Text("Watch List", modifier = Modifier.padding(16.dp))
                SavedMoviesList(
                    state = state,
                    actions = actions,
                    onNavigateToMovieDetailScreen = onNavigateToMovieDetailScreen,
                )
            },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            scaffoldState = scaffoldState,
            snackbarHost = { scaffoldState.snackbarHostState },
        ) {
            MovieList(
                onNavigateToMovieDetailScreen = onNavigateToMovieDetailScreen,
                actions = actions,
                state = state,
            )
        }
    }
}