package com.example.moviesearchapp.view.navigation

import androidx.annotation.StringRes
import com.example.moviesearchapp.R

sealed class BottomNavItem(
    @StringRes val title: Int,
    val icon: Int,
    val screenRoute: String
) {

    object Home : BottomNavItem(
        R.string.str_home,
        R.drawable.ic_home_24,
        NavigationType.HOME_SCREEN.name
    )

    object Favorite: BottomNavItem(
        R.string.str_favorite,
        R.drawable.ic_favorite_24,
        NavigationType.FAVORITE_SCREEN.name
    )
}