package com.example.data.repository.datasourceimpl

import com.example.data.api.NaverApiService
import com.example.data.model.MovieResponse
import com.example.data.repository.datasource.RemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val naverApiService: NaverApiService
) : RemoteDataSource {
    override suspend fun requestSearchMovie(
        query: String, start: Int, display: Int
    ): Response<MovieResponse> {
        return naverApiService.searchMovie(query = query, start = start, display = display)
    }
}