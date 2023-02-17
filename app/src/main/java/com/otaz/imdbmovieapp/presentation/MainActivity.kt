package com.otaz.imdbmovieapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.otaz.imdbmovieapp.presentation.navigation.Screen
import com.otaz.imdbmovieapp.presentation.ui.movie_list.MovieListScreen
import com.otaz.imdbmovieapp.presentation.ui.movie_list.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    @Inject
//    lateinit var connectivityManager: ConnectivityManager
//
//    override fun onStart() {
//        connectivityManager.registerConnectionObserver(this)
//        super.onStart()
//    }
//
//    override fun onDestroy() {
//        connectivityManager.unregisterConnectionObserver(this)
//        super.onDestroy()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.MovieList.route) {
                composable(
                    route = Screen.MovieList.route
                ) {
                    val viewModel = hiltViewModel<MovieListViewModel>()
                    MovieListScreen(
//                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                        onNavigateToMovieDetailScreen = navController::navigate,
                        viewModel = viewModel,
                    )

                }
//                composable(
//                    route = Screen.MovieDetail.route + "/{movieId}",
//                    arguments = listOf(navArgument("movieId") {
//                        type = NavType.StringType
//                    })
//                ) { navBackStackEntry ->
//                    val viewModel = hiltViewModel<MovieDetailViewModel>()
//                    MovieDetailScreen(
//                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
//                        movieId = navBackStackEntry.arguments?.getString("movieId"),
//                        viewModel = viewModel,
//                    )
//                }
            }
        }
    }
}

//            val navController = rememberNavController()
//            NavHost(navController = navController, startDestination = Screen.MovieList.route) {
//                composable(route = Screen.MovieList.route) { navBackStackEntry ->
//                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
//                    val viewModel: MovieListViewModel = viewModel({ ViewModelStore() },"RecipeListViewModel", factory)
//                    MovieListScreen(
//                        viewModel = viewModel,
//                        snackbarController = SnackbarController(lifecycleScope)
//                    )
//                }
//                composable(route = Screen.MovieDetail.route) { navBackStackEntry ->
//                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
//                    val viewModel: MovieViewModel = viewModel({ ViewModelStore() },"RecipeDetailViewModel", factory)
//                    MovieDetailScreen(
//                        movieId = "", // hard code for now
//                        viewModel = viewModel,
//                        snackbarController = SnackbarController(lifecycleScope)
//                    )
//                }
//            }