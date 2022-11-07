package com.example.moviesearchapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviesearchapp.ui.theme.ComposeMovieSearchAppTheme
import com.example.moviesearchapp.view.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMovieSearchAppTheme {
                MovieApp(mainViewModel, activity = this)
            }
        }
    }
}

@Composable
fun MovieApp(mainViewModel: MainViewModel, activity: MainActivity) {
    AppNavigation(mainViewModel = mainViewModel, activity = activity)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeMovieSearchAppTheme {

    }
}