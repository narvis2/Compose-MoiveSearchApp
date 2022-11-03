package com.example.moviesearchapp.view.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.usecase.RequestMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val requestMovieListUseCase: RequestMovieListUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _searchClick = Channel<Unit>(Channel.CONFLATED)
    val searchClick = _searchClick.receiveAsFlow()

    val getMovieList = requestMovieListUseCase(searchQuery).cachedIn(viewModelScope)

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun onClearQuery() {
        _searchQuery.value = ""
    }

    fun setIsRefreshing(isRefresh: Boolean) {
        _isRefreshing.value = isRefresh
    }

    fun onSearchClick() = viewModelScope.launch {
        _searchClick.send(Unit)
    }
}