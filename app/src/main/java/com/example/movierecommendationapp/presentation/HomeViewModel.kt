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

    private val _movieLiveData = MutableLiveData<HomeState>(HomeState(isLoading = true))
    val movieLiveData: LiveData<HomeState>
        get() = _movieLiveData

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                _movieLiveData.value = HomeState(isLoading = true)
                val movies = repository.getPopularMovies()
                movies.let {
                    _movieLiveData.value = HomeState(isLoading = false, movieData = it)
                }
            } catch (e: Exception) {
                _movieLiveData.value = HomeState(error = e.message)
            }
        }
    }

}