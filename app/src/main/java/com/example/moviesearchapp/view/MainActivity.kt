package com.example.moviesearchapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.moviesearchapp.ui.theme.ComposeMovieSearchAppTheme
import com.example.moviesearchapp.view.navigation.MovieNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMovieSearchAppTheme {
                MovieApp(coroutineScope = lifecycleScope)
            }
        }
    }
}

@Composable
fun MovieApp(coroutineScope: CoroutineScope) {
    val viewModel: MainViewModel = hiltViewModel()
    MovieNavigation(mainViewModel = viewModel, coroutineScope = coroutineScope)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeMovieSearchAppTheme {

    }
}