package com.example.data.repository

import androidx.paging.PagingData
import com.example.data.repository.datasource.RemoteDataSource
import com.example.domain.model.MovieInfoModel
import com.example.domain.repository.NaverRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NaverRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : NaverRepository {
    override fun requestMovieList(query: StateFlow<String>): Flow<PagingData<MovieInfoModel>> {
        TODO("Pagination 구현")
    }
}