package com.example.movierecommendationapp.domain.usecase

import android.content.Context
import com.example.movierecommendationapp.domain.model.Movie
import com.example.movierecommendationapp.domain.repository.MovieRepository
import com.example.movierecommendationapp.utils.NetworkUtils
import javax.inject.Inject

class GetPopularMovieUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val context: Context
) {
    suspend operator fun invoke(): List<Movie> {
        val movieData = mutableListOf<Movie>()
        if (NetworkUtils.isNetworkAvailable(context)) {
            val movie = repository.getPopularMovies()
            movieData.addAll(movie)
        } else {
            val movie = repository.getLocalPopularMovie()
            movieData.addAll(movie)
        }
        return movieData
    }
}