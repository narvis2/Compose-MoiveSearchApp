package com.example.moviesearchapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.moviesearchapp.ui.theme.ComposeMovieSearchAppTheme
import com.example.moviesearchapp.view.navigation.MovieBottomNavigation
import com.example.moviesearchapp.view.navigation.MovieNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
    val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`
    val coroutineScope = rememberCoroutineScope()
    
    val backKeyPressedTime = rememberSaveable {
        (mutableStateOf(0L))
    }

    val navController = rememberNavController()

    BackHandler() {
        if (navController.previousBackStackEntry == null || !navController.popBackStack()) {
            if (System.currentTimeMillis() > backKeyPressedTime.value + 2000) {
                backKeyPressedTime.value = System.currentTimeMillis()
                
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(message = "\"뒤로\" 버튼을 한번 더 누르면 종료됩니다.", duration = SnackbarDuration.Short)
                }
                return@BackHandler
            } else if (System.currentTimeMillis() <= backKeyPressedTime.value + 2000) {
                activity.finish()
                return@BackHandler
            }
        }
    }

    Scaffold(
        bottomBar = { MovieBottomNavigation(navController = navController) },
        scaffoldState = scaffoldState
    ) {
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