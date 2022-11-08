package com.example.moviesearchapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MovieInfoModel
import com.example.moviesearchapp.view.network.NetworkChecker
import com.example.moviesearchapp.view.network.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val _showSnackBar = MutableSharedFlow<String>(replay = 1)
    val showSnackBar = _showSnackBar.asSharedFlow()

    private val _networkState = MutableSharedFlow<NetworkState>(replay = 1)
    val networkState: SharedFlow<NetworkState> = _networkState.asSharedFlow()

    private val _currentMovieInfo = MutableStateFlow<MovieInfoModel?>(null)
    val currentMovieInfo = _currentMovieInfo.asStateFlow()

    init {
        viewModelScope.launch {
            networkChecker.networkState.collectLatest {
                _networkState.emit(it)
            }
        }
    }

    fun onRetry(
        onNetworkConnect: suspend () -> Unit,
        onNetworkDisconnect: suspend () -> Unit
    ) = viewModelScope.launch {
        networkState.collectLatest { networkType ->
            when (networkType) {
                NetworkState.Connected -> {
                    onNetworkConnect()
                }
                else -> {
                    onNetworkDisconnect()
                }
            }
        }
    }

    fun setMovieInfoModel(model: MovieInfoModel) {
        _currentMovieInfo.value = model
    }

    fun showSnackBar(message: String) = viewModelScope.launch {
        _showSnackBar.emit(message)
    }

    override fun onCleared() {
        networkChecker.unRegisterNetworkCallback()
        super.onCleared()
    }
}