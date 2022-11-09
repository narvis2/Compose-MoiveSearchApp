package com.example.moviesearchapp.view.navigation

import androidx.activity.compose.BackHandler
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
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.rememberNavController
import com.example.moviesearchapp.view.MainActivity
import com.example.moviesearchapp.view.MainViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun AppNavigation(
    mainViewModel: MainViewModel,
    activity: MainActivity,
    lifecycleOwner: LifecycleOwner
) {
    FirebaseMessaging.getInstance().token
        .addOnCompleteListener { task: Task<String?> ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }

            val token = task.result
            Timber.e("Firebase Cloud Message Token 👉 $token")
        }

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
            MovieNavigation(mainViewModel = mainViewModel, navController, lifecycleOwner)
        }
    }
}