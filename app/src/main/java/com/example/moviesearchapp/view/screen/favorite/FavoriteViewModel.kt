package com.example.moviesearchapp.view.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.RequestLocalMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val requestLocalMovieListUseCase: RequestLocalMovieListUseCase
) : ViewModel() {

    private val _isEdit = MutableStateFlow(false)
    val isEdit = _isEdit.asStateFlow()

    private val _isAllSelected = MutableStateFlow(false)
    val isAllSelected = _isAllSelected.asStateFlow()

    val getLocalMovieList = requestLocalMovieListUseCase().stateIn(
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed(5000L),
        scope = viewModelScope
    )

    fun setIsEdit(isEdit: Boolean) {
        _isEdit.value = isEdit
    }

    fun setIsAllSelected(isSelected: Boolean) {
        _isAllSelected.value = isSelected
    }
}