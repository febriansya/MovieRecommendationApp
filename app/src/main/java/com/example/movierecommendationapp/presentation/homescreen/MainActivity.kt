package com.example.movierecommendationapp.presentation.homescreen

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movierecommendationapp.data.service.DataUpdateReceiver
import com.example.movierecommendationapp.data.service.MovieUpdateService
import com.example.movierecommendationapp.databinding.ActivityMainBinding
import com.example.movierecommendationapp.domain.adapter.HomeAdapater
import com.example.movierecommendationapp.domain.model.Movie
import com.example.movierecommendationapp.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var adapterMovie: HomeAdapater
    private lateinit var binding: ActivityMainBinding
    var listOfMovie: MutableList<Movie> = mutableListOf()
    private val viewModel by viewModels<HomeViewModel>()

    private val dataUpdateReceiver = DataUpdateReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (NetworkUtils.isNetworkAvailable(this)) {
            val updateServiceIntent = Intent(this, MovieUpdateService::class.java)
            startService(updateServiceIntent)
        }

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
                binding.tvEmpty.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.tvEmpty.visibility = View.GONE
            }

            val data = it.movieData
            if (data.isNotEmpty()) {
                listOfMovie.clear()
                listOfMovie.addAll(data)
                adapterMovie.notifyDataSetChanged()
            }
        }

        viewModel.localData.observe(this){
            if (it){
                binding.tvEmpty.visibility = View.VISIBLE
            }else{

                binding.tvEmpty.visibility = View.GONE
            }
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter("DATA_UPDATED")
        LocalBroadcastManager.getInstance(this).registerReceiver(dataUpdateReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(dataUpdateReceiver)
    }

}