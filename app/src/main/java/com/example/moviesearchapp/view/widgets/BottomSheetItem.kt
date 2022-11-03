package com.example.moviesearchapp.view.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetItem(
    icon: Int,
    title: String,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .height(55.dp)
            .background(color = Color.White)
            .padding(start = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = "Share")
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = title, color = Color.Black)
        Spacer(modifier = Modifier.width(20.dp))
        Icon(imageVector = Icons.Default.Check, contentDescription = "")
    }
    Divider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
}