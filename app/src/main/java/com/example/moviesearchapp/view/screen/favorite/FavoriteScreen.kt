package com.example.moviesearchapp.view.screen.favorite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviesearchapp.R
import com.example.moviesearchapp.view.component.favorite.FavoriteEditBottomView
import com.example.moviesearchapp.view.component.favorite.FavoriteEditModeView
import com.example.moviesearchapp.view.component.favorite.FavoriteMovieItemView
import com.example.moviesearchapp.view.navigation.NavigationType
import com.example.moviesearchapp.view.widgets.ErrorOrEmptyView
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    navController: NavController,
    scaffoldState: ScaffoldState,
) {
    val favoriteList = viewModel.savedMovieList.collectAsState()

    val isEdit = viewModel.isEdit.collectAsState()
    val isAllSelect = viewModel.isAllSelected.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(), backgroundColor = Color.White
            ) {
                Box {
                    Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {

                        ProvideTextStyle(value = MaterialTheme.typography.h6) {
                            CompositionLocalProvider(
                                LocalContentAlpha provides ContentAlpha.high,
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    text = stringResource(id = R.string.str_favorite)
                                )
                            }
                        }
                    }
                }
            }
        },
        scaffoldState = scaffoldState,
    ) {
        Box(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                if (favoriteList.value.isNotEmpty()) {
                    FavoriteEditModeView(isEdit = isEdit.value,
                        isSelectedAll = isAllSelect.value,
                        onEditModeClick = {
                            if (isEdit.value && isAllSelect.value) {
                                val newList = favoriteList.value.map { model ->
                                    model.isSelected = false
                                    model
                                }

                                viewModel.setSavedMovieList(newList)
                                viewModel.setIsAllSelected(false)
                            }

                            viewModel.setIsEdit(!isEdit.value)
                        },
                        onSelectAllClick = {
                            if (!isEdit.value) {
                                viewModel.setIsEdit(true)
                            }

                            // 전체 선택 -> 모든 List 의 isSelected 를 바꿔줌
                            val newList = favoriteList.value.map { model ->
                                model.isSelected = !isAllSelect.value
                                model
                            }

                            viewModel.setSavedMovieList(newList)
                            viewModel.setIsAllSelected(!isAllSelect.value)
                        })

                    Spacer(modifier = Modifier.height(15.dp))
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    if (favoriteList.value.isEmpty()) {
                        item {
                            ErrorOrEmptyView(true)
                        }
                    } else {
                        itemsIndexed(items = favoriteList.value) { index, movie ->
                            FavoriteMovieItemView(movieInfoModel = movie,
                                isEdit = isEdit.value,
                                isSelected = favoriteList.value[index].isSelected,
                                onRootClick = {
                                    if (isEdit.value) return@FavoriteMovieItemView
                                    navController.navigate(NavigationType.DETAIL_WEB_VIEW.name + "?url=${movie.link}")
                                },
                                onSelectClick = { isSelected ->
                                    // 개별 선택 -> 해당 Index 의 isSelected 값만 바꿔줌
                                    val mutableCurrentList = favoriteList.value.toMutableList()
                                    mutableCurrentList[index] = favoriteList.value[index].copy(isSelected = !isSelected)

                                    viewModel.setSavedMovieList(mutableCurrentList)
                                })
                        }
                    }
                }
            }

            if (favoriteList.value.isNotEmpty()) {
                FavoriteEditBottomView(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    isEdit = isEdit.value,
                    isSelectedItem = favoriteList.value.filter { model -> model.isSelected }.size,
                    onDeleteAllMovie = {
                        viewModel.onDeleteAllMovie()

                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("전체 삭제를 완료하였습니다.")
                        }
                    },
                    onDeleteMovie = {
                        val mapperList = favoriteList.value.filter { model ->
                            model.isSelected
                        }.map { model ->
                            model.id
                        }

                        viewModel.onDeleteMovieListById(mapperList)

                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("${mapperList.size}개의 영화를 삭제하였습니다.")
                        }
                    }
                )
            }
        }
    }
}