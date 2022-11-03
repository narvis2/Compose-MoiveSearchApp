package com.example.moviesearchapp.view.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.moviesearchapp.R
import com.example.moviesearchapp.view.component.home.MovieInfoItemView
import com.example.moviesearchapp.view.component.home.MovieSearchBar
import com.example.moviesearchapp.view.widgets.ErrorOrEmptyView
import com.example.moviesearchapp.view.widgets.LoadingItemView
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collectLatest

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

    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = Unit) {
        homeViewModel.searchClick.collectLatest {
            focusManager.clearFocus()
            if (searchQuery.value.isEmpty()) {
                scaffoldState.snackbarHostState.showSnackbar("영화 제목을 검색해 주세요.")
                return@collectLatest
            }

            if (isRefreshing.value) {
                homeViewModel.setIsRefreshing(false)
            }

            movieList.refresh()
        }
    }

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
                onSubmitButton = {
                    homeViewModel.onSearchClick()
                },
                onSearchButtonClick = {
                    homeViewModel.onSearchClick()
                },
            ) {
                homeViewModel.onClearQuery()
            }

            Divider(modifier = Modifier.fillMaxWidth(), color = colorResource(id = R.color.orange))
            Spacer(modifier = Modifier.height(20.dp))

            SwipeRefresh(
                modifier = Modifier.padding(horizontal = 10.dp),
                state = rememberSwipeRefreshState(isRefreshing.value),
                onRefresh = {
                    if (isRefreshing.value) {
                        homeViewModel.setIsRefreshing(false)
                    }

                    homeViewModel.setIsRefreshing(true)
                    movieList.refresh()
                }
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    when {
                        // 초기 load 또는 새로고침이 실패하면 -> ERROR
                        movieList.loadState.source.refresh is LoadState.Error && movieList.itemCount == 0 -> {
                            if (isRefreshing.value) {
                                homeViewModel.setIsRefreshing(false)
                            }

                            item {
                                ErrorOrEmptyView()
                            }
                        }

                        // List 가 비어있는 경우 -> EMPTY
                        movieList.loadState.source.refresh is LoadState.NotLoading && movieList.itemCount == 0 -> {
                            if (isRefreshing.value) {
                                homeViewModel.setIsRefreshing(false)
                            }

                            item {
                                ErrorOrEmptyView()
                            }
                        }

                        // Local Db 또는 Remote 에서 새로 고침이 성공한 경우 -> VIEW
                        movieList.loadState.source.refresh is LoadState.NotLoading -> {
                            if (isRefreshing.value) {
                                homeViewModel.setIsRefreshing(false)
                            }

                            itemsIndexed(movieList) { index, movie ->
                                movie?.let {
                                    MovieInfoItemView(movieInfoModel = it) {
                                        // TODO:: Root Click Callback
                                    }
                                }
                            }
                        }

                        // Loading
                        else -> {
                            item {
                                LoadingItemView()
                            }
                        }
                    }
                }
            }
        }
    }
}