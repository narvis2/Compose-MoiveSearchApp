package com.example.moviesearchapp.view.screen.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailWebviewViewModel @Inject constructor() : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}