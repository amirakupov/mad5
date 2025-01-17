package com.example.movieappmad24.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Transaction
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieWithImages>>

    @Query("SELECT * FROM movies WHERE dbId=:id")
    fun getMovieById(id: Long): Flow<MovieWithImages>

    @Transaction
    @Query("SELECT * from movies where isFavorite= 1")
    fun getFavorites(): Flow<List<MovieWithImages>>

    @Insert
    suspend fun insertMovie(movie: Movie)

    @Update
    suspend fun update(movie: Movie)
    @Delete
    suspend fun delete(movie: Movie)


    @Insert
    suspend fun addMovie(movie: Movie)
}