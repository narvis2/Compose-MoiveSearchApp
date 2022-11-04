package com.example.domain.usecase

import com.example.domain.model.MovieInfoModel
import com.example.domain.repository.NaverRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestLocalMovieListUseCase @Inject constructor(
    private val naverRepository: NaverRepository
) {

    operator fun invoke(): Flow<List<MovieInfoModel>> {
        return naverRepository.requestLocalMovieList()
    }
}