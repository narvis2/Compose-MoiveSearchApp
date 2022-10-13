package com.example.moviesearchapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.moviesearchapp.ui.theme.ComposeMovieSearchAppTheme
import com.example.moviesearchapp.view.navigation.MovieNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMovieSearchAppTheme {
                MovieApp(mainViewModel = mainViewModel, coroutineScope = lifecycleScope)
            }
        }
    }
}

@Composable
fun MovieApp(mainViewModel: MainViewModel, coroutineScope: CoroutineScope) {
    /**
     * state holder -> MovieApp()
     * 실제 UI를 그리는 Composable -> HomeScreen()
     * --------------- 설명 ---------------------
     * UI를 직접 그리는 HomeScreen Composable 에는 ViewModel 을 직접 주입하지 않고,
     * state holder 를 담당하는 Composable 을 한 단계 거치도록 함
     */
    MovieNavigation(mainViewModel = mainViewModel, coroutineScope = coroutineScope)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeMovieSearchAppTheme {

    }
}