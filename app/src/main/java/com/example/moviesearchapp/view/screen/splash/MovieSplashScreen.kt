package com.example.moviesearchapp.view.screen.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviesearchapp.R
import com.example.moviesearchapp.view.navigation.NavigationType
import kotlinx.coroutines.delay

@Composable
fun MovieSplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(initialValue = 0f)
    }

    LaunchedEffect(key1 = Unit) {
        scale.animateTo(
            targetValue = 1.2f,
            animationSpec = tween(
                durationMillis = 800, // Animation 지속 시간
                easing = { // 시작과 끝 사이를 보간하는 데 사용되는 이징 곡선
                    OvershootInterpolator(12f).getInterpolation(it)
                }
            )
        )

        delay(2000L)
        navController.popBackStack()
        navController.navigate(NavigationType.HOME_SCREEN.name)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .padding(1.dp)
                    .scale(scale.value),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Movie,
                    contentDescription = "",
                    tint = colorResource(id = R.color.button)
                )
                
                Spacer(modifier = Modifier.width(15.dp))

                Text(
                    text = "영화 찾기 앱",
                    style = MaterialTheme.typography.h5,
                    color = colorResource(id = R.color.button)
                )
            }
        }

    }
}