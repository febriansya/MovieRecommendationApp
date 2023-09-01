package com.example.movierecommendationapp.data.repository

import com.example.movierecommendationapp.data.source.local.MovieDao
import com.example.movierecommendationapp.data.source.remote.MovieApiService
import com.example.movierecommendationapp.data.source.remote.model.getDataMovieProperties
import com.example.movierecommendationapp.data.source.remote.model.toMovieModelOffline
import com.example.movierecommendationapp.domain.model.Movie
import com.example.movierecommendationapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(val api: MovieApiService, val dao: MovieDao) :
    MovieRepository {
    override suspend fun getPopularMovies(): List<Movie> {
        val movies = api.getPopularMovies()
        val movieData = mutableListOf<Movie>()
        if (movies.isSuccessful) {
            val movieDto = movies.body()?.results
            movieDto?.let {
                for (movieDto in it) {
                    val movies = movieDto.getDataMovieProperties()
                    movieData.add(movies)
                    val saveDb = movieDto.toMovieModelOffline()
                    dao.insertMovie(saveDb)
                }
            }
        }
        return movieData
    }

    override suspend fun getLocalPopularMovie(): List<Movie> {
        val movie = dao.getAllMovie()
        val movieLocalData = mutableListOf<Movie>()

        if (movie.isNotEmpty()) {
            val mapTomovie = movie.map {
                movieLocalData.addAll(
                    listOf(
                        Movie(
                            idMovie = it.idMovie,
                            judul = it.judul,
                            tanggal = it.tanggal
                        )
                    )
                )

            }
        }

        return movieLocalData
    }
}