package com.example.data.repository.datasourceimpl

import com.example.data.db.dao.MovieDao
import com.example.data.db.entity.MovieEntity
import com.example.data.repository.datasource.LocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : LocalDataSource {
    override fun getMovieList(): Flow<List<MovieEntity>> {
        return movieDao.getMovieList()
    }

    override suspend fun getMovie(id: Long): MovieEntity {
        return movieDao.getMovie(id)
    }

    override suspend fun getMovieByTitle(title: String): MovieEntity? {
        return movieDao.getMovieByTitle(title)
    }

    override suspend fun insertMovie(movieEntity: MovieEntity): Long {
        return movieDao.insertMovie(movieEntity)
    }

    override suspend fun deleteAll() {
        movieDao.deleteAll()
    }

    override suspend fun deleteMovie(id: Long): Int {
        return movieDao.deleteMovie(id)
    }

    override suspend fun deleteMovieListById(ids: List<Long>) {
        return movieDao.deleteMovieListById(ids)
    }
}