package com.example.moviesearchapp.view.screen.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MovieInfoModel
import com.example.domain.usecase.RequestInsertMovieUseCase
import com.example.domain.usecase.RequestLocalDeleteMovieUseCase
import com.example.domain.usecase.RequestLocalMovieByTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreBottomSheetDialogViewModel @Inject constructor(
    private val requestInsertMovieUseCase: RequestInsertMovieUseCase,
    private val requestLocalDeleteMovieUseCase: RequestLocalDeleteMovieUseCase,
    private val requestLocalMovieByTitleUseCase: RequestLocalMovieByTitleUseCase
) : ViewModel() {
    private val _existMovie = MutableStateFlow<MovieInfoModel?>(null)
    val existMovie = _existMovie.asStateFlow()

    fun requestInsertMovie(movieInfoModel: MovieInfoModel) = viewModelScope.launch {
        requestInsertMovieUseCase(movieInfoModel)
    }

    fun requestDeleteMovieUseCase(id: Long) = viewModelScope.launch {
        requestLocalDeleteMovieUseCase(id)
    }

    fun requestMovieByTitleUseCase(title: String) = viewModelScope.launch {
        _existMovie.value = requestLocalMovieByTitleUseCase(title)
    }
}