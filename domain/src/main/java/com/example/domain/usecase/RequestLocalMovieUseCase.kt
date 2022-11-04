package com.example.domain.usecase

import com.example.domain.model.MovieInfoModel
import com.example.domain.repository.NaverRepository
import javax.inject.Inject

class RequestLocalMovieUseCase @Inject constructor(
    private val naverRepository: NaverRepository
) {

    suspend operator fun invoke(id: Long): MovieInfoModel {
        return naverRepository.requestLocalMovie(id)
    }
}