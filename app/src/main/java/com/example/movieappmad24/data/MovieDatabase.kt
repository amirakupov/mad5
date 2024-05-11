package com.example.movieappmad24.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.data.Converters
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.workers.DatabaseSeedWorker

@Database(entities = [Movie::class, MovieImage::class ], version = 4)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        private const val DATABASE_NAME = "movie_database"

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    DATABASE_NAME
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Schedule the worker to seed movies when the database is created
                        val workRequest = OneTimeWorkRequestBuilder<DatabaseSeedWorker>().build()
                        WorkManager.getInstance(context).enqueue(workRequest)
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
