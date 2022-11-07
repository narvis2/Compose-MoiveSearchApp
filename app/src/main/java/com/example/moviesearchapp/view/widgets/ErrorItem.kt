package com.example.moviesearchapp.view.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesearchapp.R

@Composable
fun ErrorOrEmptyView(isFavorite: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(132.dp))

        Image(painter = painterResource(id = R.drawable.search_none), contentDescription = "")

        Spacer(modifier = Modifier.height(15.7.dp))

        Text(
            text = stringResource(id = if (!isFavorite) R.string.no_result else R.string.no_favorite),
            style = TextStyle(
                color = colorResource(id = R.color.text_gray),
                fontSize = 14.sp,
                letterSpacing = (-0.01).sp,
                lineHeight = 20.sp
            )
        )
    }
}