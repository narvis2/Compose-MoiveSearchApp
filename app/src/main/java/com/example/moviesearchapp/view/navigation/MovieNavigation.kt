package com.example.moviesearchapp.view.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.plusAssign
import com.example.moviesearchapp.view.MainViewModel
import com.example.moviesearchapp.view.network.NetworkState
import com.example.moviesearchapp.view.screen.detail.DetailWebViewScreen
import com.example.moviesearchapp.view.screen.home.HomeScreen
import com.example.moviesearchapp.view.widgets.NetworkOfflineDialog
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MovieNavigation(mainViewModel: MainViewModel) {

    val navController = rememberNavController()
    val bottomSheetNavigator = rememberBottomSheetNavigator()

    navController.navigatorProvider += bottomSheetNavigator

    val networkState by mainViewModel.networkState.collectAsState(initial = NetworkState.None)
    // Snack Bar
    val scaffoldState = rememberScaffoldState()

    NetworkOfflineDialog(networkState = networkState) {
        if (scaffoldState.snackbarHostState.currentSnackbarData != null) return@NetworkOfflineDialog

        mainViewModel.onRetry(
            onNetworkConnect = {
                scaffoldState.snackbarHostState.showSnackbar("네트워크가 연결되었습니다.")
            },
            onNetworkDisconnect = {
                scaffoldState.snackbarHostState.showSnackbar("네트워크가 연결되지 않았습니다. 잠시 후 다시 시도해주세요.")
            }
        )
    }

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
    ) {
        NavHost(navController = navController, startDestination = NavigationType.HOME_SCREEN.name) {
            composable(route = NavigationType.HOME_SCREEN.name) {
                HomeScreen(navController = navController, scaffoldState = scaffoldState)
            }

            composable(route = NavigationType.DETAIL_WEB_VIEW.name + "?url={url}") { backStackEntity ->
                backStackEntity.arguments?.getString("url")?.let { url ->
                    DetailWebViewScreen(navController = navController, url = url)
                }
            }
        }
    }
}