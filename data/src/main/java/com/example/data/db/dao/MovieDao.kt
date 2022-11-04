package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.data.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM `movie` ORDER BY `movie_insert_date` DESC")
    fun getMovieList(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM `movie` WHERE `id`=:id")
    suspend fun getMovie(id: Long): MovieEntity

    @Insert(onConflict = REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity): Long

    @Query("DELETE FROM `movie`")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity): Int
}