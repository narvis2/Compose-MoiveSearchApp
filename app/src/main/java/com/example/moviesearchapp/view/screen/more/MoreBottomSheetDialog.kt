package com.example.moviesearchapp.view.screen.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.model.MovieInfoModel
import com.example.moviesearchapp.R
import com.example.moviesearchapp.view.utils.htmlToString
import com.example.moviesearchapp.view.widgets.BottomSheetHeader
import com.example.moviesearchapp.view.widgets.BottomSheetItem

@Composable
fun MovieMoreBottomSheetDialog(
    viewModel: MoreBottomSheetDialogViewModel = hiltViewModel(),
    navController: NavController,
    movieInfoModel: MovieInfoModel,
    showSnackBar: (String) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.requestMovieByTitleUseCase(movieInfoModel.title)
    }

    val existMovie = viewModel.existMovie.collectAsState()

    Column {
        BottomSheetHeader(title = movieInfoModel.title.htmlToString()) {
            navController.popBackStack()
        }

        existMovie.value?.let { model ->
            BottomSheetItem(
                imageVector = Icons.Default.Delete,
                title = stringResource(id = R.string.str_delete),
                color = colorResource(id = R.color.red)
            ) {
                viewModel.requestDeleteMovieUseCase(model.id)
                navController.popBackStack()
                showSnackBar("\"${model.title.htmlToString()}\"를 \n 즐겨찾기에서 삭제하였습니다.")
            }

            Spacer(modifier = Modifier.height(20.dp))
            return@Column
        }

        BottomSheetItem(
            imageVector = Icons.Default.Save,
            title = stringResource(R.string.str_save),
            color = colorResource(R.color.orange)
        ) {
            viewModel.requestInsertMovie(movieInfoModel)
            navController.popBackStack()
            showSnackBar("\"${movieInfoModel.title.htmlToString()}\"를 \n 즐겨찾기에 저장하였습니다.")
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}