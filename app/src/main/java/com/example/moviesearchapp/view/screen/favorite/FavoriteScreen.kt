package com.example.moviesearchapp.view.screen.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.model.MovieInfoModel
import com.example.moviesearchapp.R
import com.example.moviesearchapp.view.component.home.MovieInfoItemView
import com.example.moviesearchapp.view.navigation.NavigationType
import com.example.moviesearchapp.view.widgets.ErrorOrEmptyView

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    navController: NavController,
    scaffoldState: ScaffoldState,
    onSaveCurrentMovie: (MovieInfoModel) -> Unit
) {
    val favoriteList = viewModel.getLocalMovieList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.favorite_title))
                }, backgroundColor = Color.White
            )
        },
        scaffoldState = scaffoldState,
    ) {
        Box(modifier = Modifier.padding(it)) {
            Surface(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    if (favoriteList.value.isEmpty()) {
                        item {
                            ErrorOrEmptyView(true)
                        }
                    } else {
                        items(items = favoriteList.value) { movie ->
                            MovieInfoItemView(movieInfoModel = movie, onRootClick = {
                                navController.navigate(NavigationType.DETAIL_WEB_VIEW.name + "?url=${movie.link}")
                            }) {
                                onSaveCurrentMovie(movie)
                                navController.navigate(NavigationType.MORE_BOTTOM_SHEET.name + "?isSave=${false}")
                            }
                        }
                    }
                }
            }
        }
    }
}