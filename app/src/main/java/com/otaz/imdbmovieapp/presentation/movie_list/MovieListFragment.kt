package com.otaz.imdbmovieapp.presentation.movie_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.otaz.imdbmovieapp.presentation.components.*
import com.otaz.imdbmovieapp.presentation.components.util.SnackbarController
import com.otaz.imdbmovieapp.presentation.movie_list.MovieListEvent.NewSearchEvent
import com.otaz.imdbmovieapp.presentation.movie_list.MovieListEvent.NextPageEvent
import com.otaz.imdbmovieapp.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MovieListFragment : Fragment() {

    val viewModel : MovieListViewModel by viewModels()
    private val snackbarController = SnackbarController(lifecycleScope)

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    val keyboardController = LocalSoftwareKeyboardController.current
                    // Anytime [val movies: MutableState<List<Movie>>] from [MovieListFragment] changes, this value below
                    // [movies] will be updated here and in any composable that uses this value.
                    val movies = viewModel.movies.value
                    val loading = viewModel.loading.value

                    val page = viewModel.page.value

                    // rememberSacffoldState will create a scaffold state object and persist across recompositions
                    val scaffoldState = rememberScaffoldState()

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                expression = viewModel.expression.value,
                                onExpressionChanged = viewModel::onExpressionChanged,
                                onExecuteSearch = {
                                    if(viewModel.selectedCategory.value?.value == "Milk"){
                                        snackbarController.getScope().launch{
                                            snackbarController.showSnackbar(
                                                scaffoldState = scaffoldState,
                                                message = "Invalid Category: Milk!",
                                                actionLabel = "Hide",
                                            )
                                        }
                                    }else{
                                        viewModel.onTriggerEvent(NewSearchEvent)
                                    }
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
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colors.surface)
                        ) {
                            if (loading && movies.isEmpty()) {
                                ShimmerMovieCardItem(
                                    imageHeight = 250.dp,
                                    padding = 8.dp
                                )
                            } else {
                                LazyColumn {
                                    itemsIndexed(
                                        items = movies
                                    ){ index, movie ->
                                        viewModel.onChangeMovieScrollPosition(index)
                                        if((index + 1) >= (page * PAGE_SIZE) && !loading){
                                            viewModel.onTriggerEvent(NextPageEvent)
                                        }
                                        MovieCard(movie = movie, onClick = {})
                                    }
                                }
                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading)
                            DefaultSnackbar(
                                snackbarHostState = scaffoldState.snackbarHostState,
                                onDismiss = {
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                },
                                modifier = Modifier.align(
                                    alignment = Alignment.BottomCenter
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}