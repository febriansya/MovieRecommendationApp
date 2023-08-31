package com.example.movierecommendationapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movierecommendationapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }
}