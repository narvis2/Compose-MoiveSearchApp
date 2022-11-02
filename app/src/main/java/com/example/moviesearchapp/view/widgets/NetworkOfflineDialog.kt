package com.example.moviesearchapp.view.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(painter = painterResource(id = R.drawable.search_none_w), contentDescription = "")

                    Text(
                        modifier = Modifier.padding(top = 17.dp),
                        text = stringResource(id = R.string.str_retry_disabled_network),
                        style = TextStyle(
                            color = colorResource(
                                id = R.color.white
                            ),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    )

                    Button(
                        onClick = onRetry,
                        modifier = Modifier.padding(top = 15.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.button),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(2.dp),
                            text = "새로고침",
                            style = TextStyle(color = Color.White, fontSize = 16.sp)
                        )
                    }
                }
            }

        }
    }
}