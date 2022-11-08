package com.example.domain.usecase

import com.example.domain.repository.NaverRepository
import javax.inject.Inject

class RequestDeleteMovieListUseCase @Inject constructor(
    private val naverRepository: NaverRepository
) {

    suspend operator fun invoke(ids: List<Long>) {
        naverRepository.requestDeleteMovieListById(ids)
    }
}