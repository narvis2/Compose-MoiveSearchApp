package com.example.moviesearchapp.view.screen.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MovieInfoModel
import com.example.domain.usecase.RequestInsertMovieUseCase
import com.example.domain.usecase.RequestLocalDeleteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreBottomSheetDialogViewModel @Inject constructor(
    private val requestInsertMovieUseCase: RequestInsertMovieUseCase,
    private val requestLocalDeleteMovieUseCase: RequestLocalDeleteMovieUseCase
) : ViewModel() {

    fun requestInsertMovie(movieInfoModel: MovieInfoModel) = viewModelScope.launch {
        requestInsertMovieUseCase(movieInfoModel)
    }

    fun requestDeleteMovieUseCase(movieInfoModel: MovieInfoModel) = viewModelScope.launch {
        requestLocalDeleteMovieUseCase(movieInfoModel)
    }
}