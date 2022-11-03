package com.example.moviesearchapp.view.screen.more

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            BottomSheetHeader(title = title)

            Divider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray)

            BottomSheetItem(icon = R.drawable.btn_search_02, title = "저장") {
                
            }
            BottomSheetItem(icon = R.drawable.btn_search_02, title = "저장 삭제") {

            }
            BottomSheetItem(icon = R.drawable.btn_search_02, title = "??") {

            }
            
            Spacer(modifier = Modifier.height(20.dp))
        }

}