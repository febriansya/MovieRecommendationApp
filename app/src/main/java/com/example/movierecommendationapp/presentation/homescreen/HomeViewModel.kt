package com.example.movierecommendationapp.presentation.homescreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierecommendationapp.domain.repository.MovieRepository
import com.example.movierecommendationapp.domain.usecase.GetPopularMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val getPopularMovieUseCase: GetPopularMovieUseCase
) : ViewModel() {

    private val _movieLiveData = MutableLiveData<HomeState>(HomeState(isLoading = true))
    val movieLiveData: LiveData<HomeState>
        get() = _movieLiveData


    private val _dataExist = MutableLiveData<Boolean>()
    val localData: LiveData<Boolean>
        get() = _dataExist

    init {
        fetchPopularMovies()
        checkLocalData()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                _movieLiveData.value = HomeState(isLoading = true)
                val movies = getPopularMovieUseCase()
                movies.let {
                    _movieLiveData.value = HomeState(isLoading = false, movieData = it)
                }
            } catch (e: Exception) {
                _movieLiveData.value = HomeState(error = e.message)
            }
        }
    }

   private fun checkLocalData() {
        viewModelScope.launch {
            try {
                val movie = repository.getLocalPopularMovie()
                _dataExist.value = movie.isEmpty()
            } catch (e: Exception) {
                Log.d("checkLocal", e.message.toString())
            }
        }
    }

}