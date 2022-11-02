package com.example.moviesearchapp.view.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviesearchapp.view.MainViewModel
import com.example.moviesearchapp.view.screen.home.HomeScreen
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MovieNavigation(mainViewModel: MainViewModel) {

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    return ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
    ) {
        NavHost(navController = navController, startDestination = NavigationType.HOME_SCREEN.name) {
            composable(route = NavigationType.HOME_SCREEN.name) {
                HomeScreen(navController = navController)
            }
        }
    }
}