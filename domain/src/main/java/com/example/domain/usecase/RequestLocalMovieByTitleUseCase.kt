package com.example.domain.usecase

import com.example.domain.model.MovieInfoModel
import com.example.domain.repository.NaverRepository
import javax.inject.Inject

class RequestLocalMovieByTitleUseCase @Inject constructor(
    private val naverRepository: NaverRepository
) {

    suspend operator fun invoke(title: String): MovieInfoModel? {
        return naverRepository.requestLocalMovieByTitle(title)
    }
}