package com.example.movierecommendationapp.di

import androidx.room.Dao
import com.example.movierecommendationapp.data.repository.MovieRepositoryImpl
import com.example.movierecommendationapp.data.source.local.MovieDao
import com.example.movierecommendationapp.data.source.local.MovieDatabase
import com.example.movierecommendationapp.data.source.remote.MovieApiService
import com.example.movierecommendationapp.domain.repository.MovieRepository
import com.example.movierecommendationapp.utils.Constants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieApi(): MovieApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: MovieApiService, dao: MovieDao): MovieRepository {
        return MovieRepositoryImpl(apiService, dao)
    }
}
