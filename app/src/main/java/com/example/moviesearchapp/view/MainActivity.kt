package com.example.moviesearchapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.moviesearchapp.ui.theme.ComposeMovieSearchAppTheme
import com.example.moviesearchapp.view.navigation.MovieBottomNavigation
import com.example.moviesearchapp.view.navigation.MovieNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMovieSearchAppTheme {
                MovieApp(mainViewModel)
            }
        }
    }
}

@Composable
fun MovieApp(mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    Scaffold(bottomBar = { MovieBottomNavigation(navController = navController)}) {
        Box(modifier = Modifier.padding(it)) {
            MovieNavigation(mainViewModel = mainViewModel, navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeMovieSearchAppTheme {

    }
}