package com.example.moviesearchapp.view.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NetworkChecker @Inject constructor(
    private val context: Context
) {

    private val _networkState: MutableStateFlow<NetworkState> = MutableStateFlow(NetworkState.None)
    val networkState: StateFlow<NetworkState> = _networkState.asStateFlow()

    private val validTransportTypes: List<Int> = listOf(
        NetworkCapabilities.TRANSPORT_WIFI,
        NetworkCapabilities.TRANSPORT_CELLULAR
    )

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _networkState.value = NetworkState.Connected
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            _networkState.value = NetworkState.NotConnected
        }
    }

    private val connectivityManager: ConnectivityManager? =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    init {
        connectivityManager?.run {
            initNetworkState(this)
            registerNetworkCallback(this)
        }
    }

    private fun initNetworkState(manager: ConnectivityManager) {
        _networkState.value = manager.activeNetwork?.let {
            manager.getNetworkCapabilities(it)
        }?.let { networkCapabilities ->
            if (validTransportTypes.any { networkCapabilities.hasTransport(it) }) {
                NetworkState.Connected
            } else {
                NetworkState.NotConnected
            }
        } ?: NetworkState.NotConnected
    }

    private fun registerNetworkCallback(manager: ConnectivityManager) {
        NetworkRequest.Builder().apply {
            validTransportTypes.onEach {
                addTransportType(it)
            }
        }.let {
            manager.registerNetworkCallback(it.build(), networkCallback)
        }
    }

    fun unRegisterNetworkCallback() {
        connectivityManager?.unregisterNetworkCallback(networkCallback)
    }
}