package com.example.moviesearchapp.view.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.plusAssign
import com.example.moviesearchapp.view.MainViewModel
import com.example.moviesearchapp.view.network.NetworkState
import com.example.moviesearchapp.view.screen.detail.DetailWebViewScreen
import com.example.moviesearchapp.view.screen.favorite.FavoriteScreen
import com.example.moviesearchapp.view.screen.home.HomeScreen
import com.example.moviesearchapp.view.screen.more.MovieMoreBottomSheetDialog
import com.example.moviesearchapp.view.screen.splash.MovieSplashScreen
import com.example.moviesearchapp.view.utils.observeWithLifecycle
import com.example.moviesearchapp.view.widgets.NetworkOfflineDialog
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MovieNavigation(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    lifecycleOwner: LifecycleOwner
) {

    val bottomSheetNavigator = rememberBottomSheetNavigator()

    navController.navigatorProvider += bottomSheetNavigator

    val networkState by mainViewModel.networkState.collectAsState(initial = NetworkState.None)
    // Snack Bar
    val scaffoldState = rememberScaffoldState()

    val currentMovieInfoModel = mainViewModel.currentMovieInfo.collectAsState()

    mainViewModel.showSnackBar.observeWithLifecycle(lifecycleOwner = lifecycleOwner) {
        scaffoldState.snackbarHostState.showSnackbar(it)
    }

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
        NavHost(navController = navController, startDestination = NavigationType.SPLASH_SCREEN.name) {
            composable(route = NavigationType.SPLASH_SCREEN.name) {
                MovieSplashScreen(navController = navController)
            }

            composable(route = NavigationType.HOME_SCREEN.name) {
                HomeScreen(navController = navController, scaffoldState = scaffoldState) { model ->
                    mainViewModel.setMovieInfoModel(model)
                }
            }

            composable(route = NavigationType.DETAIL_WEB_VIEW.name + "?url={url}") { backStackEntity ->
                backStackEntity.arguments?.getString("url")?.let { url ->
                    DetailWebViewScreen(navController = navController, url = url)
                }
            }
            
            composable(route = NavigationType.FAVORITE_SCREEN.name) {
                FavoriteScreen(navController = navController, scaffoldState = scaffoldState)
            }

            bottomSheet(
                route = NavigationType.MORE_BOTTOM_SHEET.name
            ) {
                currentMovieInfoModel.value?.let { currentModel ->
                    MovieMoreBottomSheetDialog(
                        navController = navController,
                        movieInfoModel = currentModel,
                        showSnackBar = {
                            mainViewModel.showSnackBar(it)
                        }
                    )
                }
            }
        }
    }
}