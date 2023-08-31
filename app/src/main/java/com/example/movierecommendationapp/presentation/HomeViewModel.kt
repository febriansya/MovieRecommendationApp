package com.example.movierecommendationapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierecommendationapp.domain.model.Movie
import com.example.movierecommendationapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    init {
        fetchPopularMovies()
    }


    private val _movieLiveData = MutableLiveData<List<Movie>>()
    val movieLiveData: LiveData<List<Movie>> = _movieLiveData

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            val movies = repository.getPopularMovies()
            _movieLiveData.value = movies
        }
    }

}