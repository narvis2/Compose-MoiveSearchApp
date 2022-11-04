package com.example.domain.usecase

import com.example.domain.repository.NaverRepository
import javax.inject.Inject

class RequestLocalDeleteMovieAllUseCase @Inject constructor(
    private val naverRepository: NaverRepository
) {

    suspend operator fun invoke() {
        naverRepository.requestLocalDeleteMovieAll()
    }
}