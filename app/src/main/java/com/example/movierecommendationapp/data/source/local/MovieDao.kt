package com.example.movierecommendationapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movierecommendationapp.data.source.local.model.MovieModel

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun getAllMovie(): List<MovieModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieModel: MovieModel)

}