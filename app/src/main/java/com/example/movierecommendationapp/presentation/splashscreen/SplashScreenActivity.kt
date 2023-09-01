package com.example.movierecommendationapp.presentation.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.movierecommendationapp.data.service.MovieUpdateService
import com.example.movierecommendationapp.presentation.homescreen.MainActivity
import com.example.movierecommendationapp.databinding.ActivitySplashScreenBinding
import com.example.movierecommendationapp.presentation.homescreen.HomeViewModel
import com.example.movierecommendationapp.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (NetworkUtils.isNetworkAvailable(this)) {

        } else {
           viewModel.localData.observe(this){
                if (it){
                    Toast.makeText(this, "No connection, and  Local Data Empty!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "No connection, You Use Local Data!", Toast.LENGTH_SHORT).show()
                }
            }
        }


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}