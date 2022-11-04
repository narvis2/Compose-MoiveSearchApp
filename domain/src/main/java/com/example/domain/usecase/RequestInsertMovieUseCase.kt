package com.example.domain.usecase

import com.example.domain.model.MovieInfoModel
import com.example.domain.repository.NaverRepository
import javax.inject.Inject

class RequestInsertMovieUseCase @Inject constructor(
    private val naverRepository: NaverRepository
) {

    suspend operator fun invoke(movieInfoModel: MovieInfoModel): Long {
        return naverRepository.requestInsertMovie(movieInfoModel)
    }
}