package com.example.data.repository.datasource

import com.example.data.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getMovieList(): Flow<List<MovieEntity>>

    suspend fun getMovie(id: Long): MovieEntity

    suspend fun getMovieByTitle(title: String): MovieEntity?

    suspend fun insertMovie(movieEntity: MovieEntity): Long

    suspend fun deleteAll()

    suspend fun deleteMovie(id: Long): Int

    suspend fun deleteMovieListById(ids: List<Long>)
}