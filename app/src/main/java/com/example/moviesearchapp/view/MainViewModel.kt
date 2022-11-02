package com.example.moviesearchapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearchapp.view.network.NetworkChecker
import com.example.moviesearchapp.view.network.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val _networkState = MutableSharedFlow<NetworkState>(replay = 1)
    val networkState: SharedFlow<NetworkState> = _networkState.asSharedFlow()

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

    override fun onCleared() {
        networkChecker.unRegisterNetworkCallback()
        super.onCleared()
    }
}