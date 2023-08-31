package com.example.movierecommendationapp.data.source.remote

import com.example.movierecommendationapp.data.source.remote.model.MovieDto
import retrofit2.Response
import retrofit2.http.GET

interface MovieApiService {
    @GET("discover/movie?api_key=f7b67d9afdb3c971d4419fa4cb667fbf")
    suspend fun getPopularMovies():Response<MovieDto>
}