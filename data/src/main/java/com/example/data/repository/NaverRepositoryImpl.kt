package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.mapper.ObjectMapper.entityToMovieInfoModelList
import com.example.data.mapper.ObjectMapper.toMovieEntity
import com.example.data.mapper.ObjectMapper.toMovieInfoModel
import com.example.data.repository.datasource.LocalDataSource
import com.example.data.repository.datasource.RemoteDataSource
import com.example.data.repository.datasourceimpl.MovieInfoListPagingSource
import com.example.domain.model.MovieInfoModel
import com.example.domain.repository.NaverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class NaverRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : NaverRepository {
    override fun requestMovieList(query: StateFlow<String>): Flow<PagingData<MovieInfoModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                MovieInfoListPagingSource(
                    remoteDataSource = remoteDataSource,
                    searchQuery = query,
                    limit = 10
                )
            }
        ).flow.distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    override fun requestLocalMovieList(): Flow<List<MovieInfoModel>> {
        return localDataSource.getMovieList().map {
            it.entityToMovieInfoModelList()
        }.flowOn(Dispatchers.IO).distinctUntilChanged()
    }

    override suspend fun requestLocalMovie(id: Long): MovieInfoModel {
        return localDataSource.getMovie(id).toMovieInfoModel()
    }

    override suspend fun requestInsertMovie(movieInfoModel: MovieInfoModel): Long {
        return localDataSource.insertMovie(movieInfoModel.toMovieEntity())
    }

    override suspend fun requestLocalDeleteMovieAll() {
        localDataSource.deleteAll()
    }

    override suspend fun requestLocalDeleteMovie(movieInfoModel: MovieInfoModel): Int {
        return localDataSource.deleteMovie(movieInfoModel.toMovieEntity())
    }

    override suspend fun requestDeleteMovieListById(ids: List<Long>) {
        return localDataSource.deleteMovieListById(ids)
    }
}