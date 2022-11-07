package com.example.moviesearchapp.view.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.RequestLocalMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val requestLocalMovieListUseCase: RequestLocalMovieListUseCase
) : ViewModel() {

    val getLocalMovieList = requestLocalMovieListUseCase().stateIn(
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(5000L),
        scope = viewModelScope
    )
}