package com.otaz.imdbmovieapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.otaz.imdbmovieapp.di.BaseApplication
import com.otaz.imdbmovieapp.presentation.navigation.Screen
import com.otaz.imdbmovieapp.presentation.ui.movie_detail.MovieDetailScreen
import com.otaz.imdbmovieapp.presentation.ui.movie_detail.MovieDetailViewModel
import com.otaz.imdbmovieapp.presentation.ui.movie_game.MovieGameScreen
import com.otaz.imdbmovieapp.presentation.ui.movie_game.MovieGameViewModel
import com.otaz.imdbmovieapp.presentation.ui.movie_list.MovieListScreen
import com.otaz.imdbmovieapp.presentation.ui.movie_list.MovieListViewModel
import com.otaz.imdbmovieapp.presentation.ui.saved_movie_list.SavedMovieListViewModel
import com.otaz.imdbmovieapp.presentation.ui.splash_screen.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * This application uses one single activity [MainActivity] with compose only navigation.
 * The NavHostController is passed eventually to the MovieListView where it receives it's route.
 * The route is a combination of the base string, defined in [Screen.MovieDetail], and the id of
 * the specific movie which is clicked.
 *
 * Ex: "movieList/488439"
 *
 * This ID is specific to the TMDB API and is subsequently used to acquire
 * movie specifications as a separate call to TMDB API which includes the movies respective IMDB ID.
 * The IMDB ID is then used to retrieve further desired data from the OMDB API.
 *
 * Combining both APIs are necessary to retrieve the desired information as well as desired API
 * features such as pagination, adult content filtering, language adjustment etc.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var app: BaseApplication
    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.MovieList.route) {

                // MovieListScreen
                composable(
                    route = Screen.MovieList.route,
                ) {
                    val movieListViewModel = hiltViewModel<MovieListViewModel>()
                    val savedMovieListViewModel = hiltViewModel<SavedMovieListViewModel>()

                    MovieListScreen(
                        navController = navController,
//                        onNavigateToMovieDetailScreen = navController::navigate,
                        movieListViewModel = movieListViewModel,
                        savedMovieListViewModel = savedMovieListViewModel,
                    )
                }

                // MovieListScreen
                composable(
                    route = Screen.MovieDetail.route + "/{movieId}",
                    arguments = listOf(navArgument("movieId") {
                        type = NavType.IntType
                    })
                ) { navBackStackEntry ->
                    val movieDetailViewModel = hiltViewModel<MovieDetailViewModel>()
                    MovieDetailScreen(
                        movieId = navBackStackEntry.arguments?.getInt("movieId"),
                        viewModel = movieDetailViewModel,
                    )
                }

                // MovieGameScreen
                composable(
                    route = Screen.MovieGame.route
                ){
                val movieGameViewModel = hiltViewModel<MovieGameViewModel>()
                    MovieGameScreen(
                        viewModel = movieGameViewModel,
                    )
                }
            }
        }
    }
}