package com.example.moviesearchapp.view.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.moviesearchapp.R

@Composable
fun MovieBottomNavigation(navController: NavHostController) {
    val items: List<BottomNavItem> = listOf(
        BottomNavItem.Home,
        BottomNavItem.Favorite
    )

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    /**
     * Bottom Navigation Bar 숨기기
     */
    when (navBackStackEntry?.destination?.route) {
        NavigationType.HOME_SCREEN.name -> {
            bottomBarState.value = true
        }

        NavigationType.FAVORITE_SCREEN.name -> {
            bottomBarState.value = true
        }

        else -> {
            bottomBarState.value = false
        }
    }
    
    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomNavigation(
            backgroundColor = Color.White
        ) {
            items.forEach { bottomItem ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = bottomItem.icon),
                            contentDescription = stringResource(id = bottomItem.title),
                            modifier = Modifier
                                .width(26.dp)
                                .height(26.dp)
                        )
                    },
                    selected = currentRoute == bottomItem.screenRoute,
                    selectedContentColor = colorResource(id = R.color.button),
                    unselectedContentColor = Color.LightGray,
                    alwaysShowLabel = false,
                    onClick = {
                        navController.navigate(bottomItem.screenRoute) {
                            navController.graph.startDestinationRoute?.let {
                                // popUpTo()를 통해 startDestinationRoute 만 스택에 쌓일 수 있게
                                popUpTo(it) {
                                    saveState = true
                                }
                            }

                            launchSingleTop = true // 화면 인스턴스 하나만 만들어지게
                            restoreState = true // 버튼을 재클릭했을 때 이전 상태가 남아있게
                        }
                    }
                )
            }
        }
    }
}