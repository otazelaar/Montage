package com.otaz.montage.presentation.ui.movie_list

import com.otaz.montage.interactors.movie_list.GetMostPopularMovies

interface PaginationManager {
    fun getPopularMovies()
}