package com.example.movieappmad24.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.getMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseSeedWorker(
    context: Context,
    params: WorkerParameters,
    private val movieRepository: MovieRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        // Seed movies in the background
        seedMovies()
        return Result.success()
    }

    private suspend fun seedMovies() {
        withContext(Dispatchers.IO) {
            // Seed your movies here
            val movies = getMovies() // Assuming you have a function to get movie data
            movies.forEach { movie ->
                movieRepository.addMovie(movie)
            }
        }
    }
}