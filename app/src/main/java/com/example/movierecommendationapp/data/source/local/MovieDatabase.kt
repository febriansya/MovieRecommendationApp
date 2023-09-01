package com.example.movierecommendationapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movierecommendationapp.data.source.local.model.MovieModel

@Database(entities = [MovieModel::class], exportSchema = false, version = 1)
abstract class MovieDatabase() : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}


