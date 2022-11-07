package com.example.moviesearchapp.view.screen.favorite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviesearchapp.R
import com.example.moviesearchapp.view.component.favorite.FavoriteEditModeView
import com.example.moviesearchapp.view.component.favorite.FavoriteMovieItemView
import com.example.moviesearchapp.view.navigation.NavigationType
import com.example.moviesearchapp.view.widgets.ErrorOrEmptyView

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    navController: NavController,
    scaffoldState: ScaffoldState,
) {
    val favoriteList = viewModel.savedMovieList.collectAsState()

    val isEdit = viewModel.isEdit.collectAsState()
    val isAllSelect = viewModel.isAllSelected.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.White
            ) {
                Box() {
                    Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {

                        ProvideTextStyle(value = MaterialTheme.typography.h6) {
                            CompositionLocalProvider(
                                LocalContentAlpha provides ContentAlpha.high,
                            ){
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
                    FavoriteEditModeView(
                        isEdit = isEdit.value,
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

                            val newList = favoriteList.value.map { model ->
                                model.isSelected = !isAllSelect.value
                                model
                            }

                            viewModel.setSavedMovieList(newList)
                            viewModel.setIsAllSelected(!isAllSelect.value)
                        }
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        if (favoriteList.value.isEmpty()) {
                            item {
                                ErrorOrEmptyView(true)
                            }
                        } else {
                            itemsIndexed(items = favoriteList.value) { index, movie ->
                                FavoriteMovieItemView(
                                    movieInfoModel = movie,
                                    isEdit = isEdit.value,
                                    isSelected = favoriteList.value[index].isSelected,
                                    onRootClick = {
                                        if (isEdit.value) return@FavoriteMovieItemView
                                        navController.navigate(NavigationType.DETAIL_WEB_VIEW.name + "?url=${movie.link}")
                                    },
                                    onSelectClick = { isSelected ->
                                        // 개별 선택
                                        val mutableCurrentList = favoriteList.value.toMutableList()
                                        mutableCurrentList[index] = favoriteList.value[index].copy(isSelected = !isSelected)

                                        viewModel.setSavedMovieList(mutableCurrentList)
                                    }
                                )
                            }
                        }
                    }
                }
        }
    }
}