package com.example.movierecommendationapp.domain.repository

import com.example.movierecommendationapp.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(): List<Movie>
}