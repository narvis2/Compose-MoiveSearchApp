package com.example.moviesearchapp.view.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.moviesearchapp.R
import com.example.moviesearchapp.view.component.home.MovieSearchBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import timber.log.Timber

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
) {
    val searchQuery = homeViewModel.searchQuery.collectAsState()
    val movieList = homeViewModel.getMovieList.collectAsLazyPagingItems()
    val isRefreshing = homeViewModel.isRefreshing.collectAsState()

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
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MovieSearchBar(
                searchValue = searchQuery.value,
                onChangeSearchValue = homeViewModel::setSearchQuery,
                onSubmitButton = { /* TODO */ },
                onSearchButtonClick = {/* TODO */},
            ) {
                homeViewModel.onClearQuery()
            }

            Divider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(20.dp))

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing.value),
                onRefresh = {
                    movieList.refresh()
                    homeViewModel.setIsRefreshing(true)
                }
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(movieList) { movie ->
                        movie?.let {
                            Timber.e("🍎 MovieInfoModel -> $it")
                        }
                    }

                    when {
                        // 초기 load 또는 새로고침이 실패하면 -> ERROR
                        movieList.loadState.source.refresh is LoadState.Error && movieList.itemCount == 0 -> {
                            if (isRefreshing.value) {
                                homeViewModel.setIsRefreshing(false)
                            }

                            // TODO:: SHOW ERROR VIEW
                        }

                        // List 가 비어있는 경우 -> EMPTY
                        movieList.loadState.source.refresh is LoadState.NotLoading && movieList.itemCount == 0 -> {
                            if (isRefreshing.value) {
                                homeViewModel.setIsRefreshing(false)
                            }

                            // TODO:: SHOW EMPTY VIEW
                        }

                        // Local Db 또는 Remote 에서 새로 고침이 성공한 경우 -> VIEW
                        movieList.loadState.source.refresh is LoadState.NotLoading -> {
                            if (isRefreshing.value) {
                                homeViewModel.setIsRefreshing(false)
                            }
                            
                        }

                        // Loading
                        else -> {

                        }
                    }
                }
            }
        }
    }
}