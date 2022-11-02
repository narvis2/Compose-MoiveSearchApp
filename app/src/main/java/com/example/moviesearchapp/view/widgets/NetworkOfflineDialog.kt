package com.example.moviesearchapp.view.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.moviesearchapp.R
import com.example.moviesearchapp.view.network.NetworkState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NetworkOfflineDialog(
    networkState: NetworkState, onRetry: () -> Unit
) {
    if (networkState is NetworkState.NotConnected) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(usePlatformDefaultWidth = false), // 다이얼로그 기본 Padding 제거
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = colorResource(id = R.color.transparency_black)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "네트워크가 끊어졌습니다.", style = TextStyle(
                            color = colorResource(
                                id = R.color.white
                            ), fontSize = 16.sp, textAlign = TextAlign.Center
                        )
                    )

                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = "네트워크 연결을 확인해 주세요.", style = TextStyle(
                            color = colorResource(
                                id = R.color.white
                            ), fontSize = 16.sp, textAlign = TextAlign.Center
                        )
                    )

                    Button(onClick = onRetry, modifier = Modifier.padding(top = 10.dp)) {
                        Text(text = "재시도")
                    }
                }
            }

        }
    }
}