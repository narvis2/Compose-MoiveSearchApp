package com.example.moviesearchapp.view.screen.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MovieInfoModel
import com.example.domain.usecase.RequestInsertMovieUseCase
import com.example.domain.usecase.RequestLocalDeleteMovieUseCase
import com.example.domain.usecase.RequestLocalMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreBottomSheetDialogViewModel @Inject constructor(
    private val requestLocalGetMovieUseCase: RequestLocalMovieListUseCase,
    private val requestInsertMovieUseCase: RequestInsertMovieUseCase,
    private val requestLocalDeleteMovieUseCase: RequestLocalDeleteMovieUseCase
) : ViewModel() {

    fun requestInsertMovie(movieInfoModel: MovieInfoModel, onSuccess: () -> Unit) = viewModelScope.launch {
        requestInsertMovieUseCase(movieInfoModel)
        onSuccess()
    }

    fun requestDeleteMovieUseCase(movieInfoModel: MovieInfoModel) = viewModelScope.launch {
        requestLocalDeleteMovieUseCase(movieInfoModel)
    }
}