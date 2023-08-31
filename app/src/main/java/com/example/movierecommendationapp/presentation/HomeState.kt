package com.example.movierecommendationapp.presentation

import com.example.movierecommendationapp.domain.model.Movie

data class HomeState(
    val movieData: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)