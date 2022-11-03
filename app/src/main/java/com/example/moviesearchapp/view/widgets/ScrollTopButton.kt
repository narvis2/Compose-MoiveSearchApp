package com.example.moviesearchapp.view.widgets

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.moviesearchapp.R

@Composable
fun ScrollTopButton(onScrollTop: () -> Unit) {
    IconButton(onClick = onScrollTop) {
        Icon(painter = painterResource(id = R.drawable.ico_top), contentDescription = "")
    }
}