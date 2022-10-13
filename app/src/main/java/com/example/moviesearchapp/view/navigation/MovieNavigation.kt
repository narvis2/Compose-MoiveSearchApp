package com.example.moviesearchapp.view.navigation

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviesearchapp.view.MainViewModel
import com.example.moviesearchapp.view.screen.home.HomeScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun MovieNavigation(mainViewModel: MainViewModel, coroutineScope: CoroutineScope) {
    val navController = rememberNavController()

    // Snack Bar
    val scaffoldState = rememberScaffoldState()

    // Home
    val searchQuery = mainViewModel.searchQuery.collectAsState()

    return NavHost(navController = navController, startDestination = NavigationType.HOME_SCREEN.name) {
        composable(route = NavigationType.HOME_SCREEN.name) {
            HomeScreen(
                navController = navController,
                scaffoldState = scaffoldState,
                searchQuery = searchQuery.value,
                setSearchQuery = mainViewModel::setSearchQuery,
                onClearQuery = mainViewModel::onClearQuery
            )
        }
    }
}