package com.example.movierecommendationapp.di

import android.content.Context
import com.example.movierecommendationapp.data.repository.MovieRepositoryImpl
import com.example.movierecommendationapp.data.source.local.MovieDao
import com.example.movierecommendationapp.data.source.remote.MovieApiService
import com.example.movierecommendationapp.domain.repository.MovieRepository
import com.example.movierecommendationapp.domain.usecase.GetPopularMovieUseCase
import com.example.movierecommendationapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context = appContext

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
