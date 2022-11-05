package com.example.moviesearchapp.view.screen.favorite

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviesearchapp.R

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    navController: NavController,
    scaffoldState: ScaffoldState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.favorite_title))
                },
                backgroundColor = Color.White
            )
        },
        scaffoldState = scaffoldState,
    ) {
        Surface(modifier = Modifier.padding(it)) {

        }
    }
}