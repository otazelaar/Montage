package com.otaz.imdbmovieapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.otaz.imdbmovieapp.presentation.navigation.Screen
import com.otaz.imdbmovieapp.presentation.ui.movie.MovieDetailScreen
import com.otaz.imdbmovieapp.presentation.ui.movie.MovieDetailViewModel
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
                        onNavigateToMovieDetailScreen = navController::navigate,
                        viewModel = viewModel,
                    )

                }
                composable(
                    route = Screen.MovieDetail.route + "/{movieId}",
                    arguments = listOf(navArgument("movieId") {
                        type = NavType.IntType
                    })
                ) { navBackStackEntry ->
                    val viewModel = hiltViewModel<MovieDetailViewModel>()
                    MovieDetailScreen(
                        movieId = navBackStackEntry.arguments?.getInt("movieId"),
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}