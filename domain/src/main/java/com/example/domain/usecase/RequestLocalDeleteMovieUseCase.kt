package com.example.domain.usecase

import com.example.domain.model.MovieInfoModel
import com.example.domain.repository.NaverRepository
import javax.inject.Inject

class RequestLocalDeleteMovieUseCase @Inject constructor(
    private val naverRepository: NaverRepository
) {

    suspend operator fun invoke(movieInfoModel: MovieInfoModel): Long {
        return naverRepository.requestLocalDeleteMovie(movieInfoModel)
    }
}