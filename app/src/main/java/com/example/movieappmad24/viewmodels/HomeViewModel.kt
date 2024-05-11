package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movies = MutableStateFlow<List<MovieWithImages>>(emptyList())
    val movies: StateFlow<List<MovieWithImages>> = _movies

    fun getAllMoviesWithImages() {
        viewModelScope.launch {
            repository.getAllMovies().collect { movies ->
                _movies.value = movies
            }
        }
    }
}