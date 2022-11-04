package com.example.moviesearchapp.view.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomSheetHeader(title: String, onBackPress: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(start = 20.dp)
        )

        IconButton(onClick = onBackPress, modifier = Modifier.padding(end = 10.dp)) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "")
        }
    }
}

@Composable
fun BottomSheetItem(
    imageVector: ImageVector,
    title: String,
    color: Color,
    onItemClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .height(55.dp)
            .background(color = Color.White)
            .padding(start = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Share",
            modifier = Modifier.padding(start = 10.dp),
            tint = color
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = title, color = Color.Black)
    }
}