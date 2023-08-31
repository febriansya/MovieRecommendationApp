package com.example.movierecommendationapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movierecommendationapp.databinding.ActivityMainBinding
import com.example.movierecommendationapp.domain.adapter.HomeAdapater
import com.example.movierecommendationapp.domain.model.Movie
import com.example.movierecommendationapp.presentation.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var adapterMovie: HomeAdapater
    private lateinit var binding: ActivityMainBinding
    var listOfMovie: MutableList<Movie> = mutableListOf()
    private val viewModel by viewModels<HomeViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeData()

        adapterMovie = HomeAdapater(listOfMovie as ArrayList<Movie>)
        binding.rvMovie.setHasFixedSize(true)
        binding.rvMovie.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvMovie.adapter = adapterMovie
    }


    @SuppressLint("NotifyDataSetChanged")
    fun observeData() {
        viewModel.movieLiveData.observe(this) {
            if (it.isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }

            val data = it.movieData
            if (data.isNotEmpty()) {
                listOfMovie.clear()
                listOfMovie.addAll(data)
                adapterMovie.notifyDataSetChanged()
            }
        }
    }

}