package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WatchlistViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _watchlistMovies = MutableStateFlow<List<MovieWithImages>>(emptyList())
    val watchlistMovies: StateFlow<List<MovieWithImages>> = _watchlistMovies

    fun getFavoriteMovies() {
        viewModelScope.launch {
            repository.getFavoriteMovies().collect { movies ->
                _watchlistMovies.value = movies
            }
        }
    }
}