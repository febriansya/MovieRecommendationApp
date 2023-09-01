package com.example.movierecommendationapp.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieModel(
    @PrimaryKey val idMovie: Int,
    val judul: String,
    val tanggal: String
)