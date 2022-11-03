package com.example.moviesearchapp.view.screen.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import com.example.moviesearchapp.R
import com.example.moviesearchapp.view.widgets.BottomSheetItem

@Composable
fun MovieMoreBottomSheetDialog(
    navController: NavController
) {
        Column() {
            BottomSheetItem(icon = R.drawable.btn_search_02, title = "저장") {
                
            }
            BottomSheetItem(icon = R.drawable.btn_search_02, title = "저장 삭제") {

            }
            BottomSheetItem(icon = R.drawable.btn_search_02, title = "??") {

            }
        }

}