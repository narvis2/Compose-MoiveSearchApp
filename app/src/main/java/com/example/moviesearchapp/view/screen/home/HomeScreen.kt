package com.example.moviesearchapp.view.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.moviesearchapp.R
import com.example.moviesearchapp.view.component.home.MovieSearchBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    searchQuery: String,
    setSearchQuery: (String) -> Unit,
    onClearQuery: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.home_title))
                },
                backgroundColor = Color.White
            )
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MovieSearchBar(
                searchValue = searchQuery,
                onChangeSearchValue = setSearchQuery,
                onSubmitButton = { /* TODO */ },
                onSearchButtonClick = {/* TODO */},
            ) {
                onClearQuery()
            }
        }
    }
}