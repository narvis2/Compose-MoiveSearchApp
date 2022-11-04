package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.MovieInfoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface NaverRepository {
    fun requestMovieList(query: StateFlow<String>): Flow<PagingData<MovieInfoModel>>

    fun requestLocalMovieList(): Flow<List<MovieInfoModel>>

    suspend fun requestLocalMovie(id: Long): MovieInfoModel

    suspend fun requestInsertMovie(movieInfoModel: MovieInfoModel): Long

    suspend fun requestLocalDeleteMovieAll()

    suspend fun requestLocalDeleteMovie(movieInfoModel: MovieInfoModel): Int
}