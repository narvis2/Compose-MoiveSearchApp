package com.example.moviesearchapp.view

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.RequestMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val requestMovieListUseCase: RequestMovieListUseCase
) : ViewModel() {
}