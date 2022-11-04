package com.example.moviesearchapp.view.screen.more

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import com.example.moviesearchapp.R
import com.example.moviesearchapp.view.widgets.BottomSheetHeader
import com.example.moviesearchapp.view.widgets.BottomSheetItem

@Composable
fun MovieMoreBottomSheetDialog(
    navController: NavController,
    title: String
) {
        Column() {
            BottomSheetHeader(title = title) {
                navController.popBackStack()
            }

            BottomSheetItem(
                imageVector = Icons.Default.Save,
                title = stringResource(id = R.string.str_save),
                color = colorResource(id = R.color.orange)
            ) {
                
            }
            BottomSheetItem(
                imageVector = Icons.Default.Delete,
                title = stringResource(id = R.string.str_delete),
                color = colorResource(id = R.color.red)
            ) {

            }

            Spacer(modifier = Modifier.height(20.dp))
        }

}