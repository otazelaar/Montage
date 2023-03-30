package com.otaz.imdbmovieapp.presentation.ui.movie_game

/**
 * This class is used to trigger events related to the MovieGame
 */

sealed class MovieGameEvent {
    object NewRandomMovieEvent: MovieGameEvent()
}