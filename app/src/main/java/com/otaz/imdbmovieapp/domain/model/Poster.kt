package com.otaz.imdbmovieapp.domain.model

import java.io.Serializable

data class Poster (
    var id: String,
    var link: String,
    var aspectRatio: Int,
    var language: String,
    var width: Int,
    var height: Int,
) : Serializable