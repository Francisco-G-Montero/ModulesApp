package com.example.feature_lists.data.model

const val TYPE_MOVIE_TERROR = 0
const val TYPE_MOVIE_DRAMA = 1
const val TYPE_HEADER = 2

sealed class CinemaModel {
    data class TerrorMovie(
        val title: String,
        val color: Int
    ) : CinemaModel()
    data class DramaMovie(
        val title: String,
        val color: Int,
        val showElement: Boolean = true
    ) : CinemaModel()
    data class Header(
        val title: String,
    ) : CinemaModel()
}
