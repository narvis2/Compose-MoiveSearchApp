package com.example.moviesearchapp.view.component.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.domain.model.MovieInfoModel
import com.example.moviesearchapp.R
import com.example.moviesearchapp.view.utils.htmlToString
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

@Composable
fun MovieSearchBar(
    modifier: Modifier = Modifier,
    searchValue: String,
    onChangeSearchValue: (String) -> Unit,
    onSubmitButton: () -> Unit,
    onSearchButtonClick: () -> Unit,
    onResetSearchButton: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = modifier.padding(bottom = 8.dp),
            value = searchValue,
            onValueChange = onChangeSearchValue,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.orange),
                unfocusedBorderColor = colorResource(id = R.color.white),
                focusedLabelColor = colorResource(id = R.color.orange),
                errorBorderColor = colorResource(id = R.color.red),
                errorLabelColor = colorResource(id = R.color.red)
            ),
            isError = searchValue.length < 2,
            singleLine = true,
            trailingIcon = {
                if (searchValue.isNotEmpty()) {
                    IconButton(onClick = { onResetSearchButton() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "close",
                            tint = colorResource(R.color.orange),
                        )
                    }
                }
            },
            label = { Text(text = stringResource(id = R.string.movie_search_helper)) },
            placeholder = { Text(text = stringResource(id = R.string.movie_search_place_holder)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {
                onSubmitButton()
            })
        )
        OutlinedButton(
            modifier = modifier.padding(start = 10.dp),
            onClick = { onSearchButtonClick() },
            border = BorderStroke(
                1.dp,
                if (searchValue.length < 2) colorResource(id = R.color.red) else colorResource(id = R.color.orange)
            ),
            enabled = searchValue.isNotEmpty()
        ) {
            Text(
                text = "검색", textAlign = TextAlign.Center, style = TextStyle(
                    if (searchValue.length < 2) colorResource(id = R.color.red) else colorResource(
                        id = R.color.orange
                    )
                )
            )
        }
    }
}

@Composable
fun MovieInfoItemView(
    modifier: Modifier = Modifier,
    movieInfoModel: MovieInfoModel,
    onRootClick: () -> Unit,
    onActionMoreClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable {
                onRootClick()
            }
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movieInfoModel.image)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.search_none),
            error = painterResource(id = R.drawable.search_none),
            contentDescription = "",
            modifier = modifier.size(90.dp),
            contentScale = ContentScale.Fit
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
        ) {
            Column {
                Text(
                    text = movieInfoModel.title.htmlToString(),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )

                RatingBar(
                    value = movieInfoModel.rating,
                    config = RatingBarConfig()
                        .activeColor(colorResource(id = R.color.orange))
                        .inactiveColor(Color.LightGray)
                        .stepSize(StepSize.HALF)
                        .numStars(5)
                        .isIndicator(true)
                        .size(13.dp)
                        .style(RatingBarStyle.HighLighted)
                    ,
                    onValueChange = {},
                    onRatingChanged = {}
                )

                Text(text = movieInfoModel.pubDate, fontSize = 13.sp)

                Text(
                    text = stringResource(id = R.string.str_director, movieInfoModel.director),
                    fontSize = 13.sp
                )

                Text(
                    text = if (movieInfoModel.actor.isEmpty()) {
                        stringResource(id = R.string.str_no_actors)
                    } else {
                        stringResource(id = R.string.str_actors, movieInfoModel.actor)
                    },
                    fontSize = 13.sp
                )
            }

            Image(
                modifier = modifier
                    .align(Alignment.TopEnd)
                    .clickable {
                        onActionMoreClick()
                    }
                    .padding(start = 16.dp, bottom = 16.dp, top = 5.dp, end = 5.dp),
                painter = painterResource(id = R.drawable.btn_more_02),
                contentDescription = ""
            )
        }
    }
    Divider(modifier = Modifier.fillMaxWidth())
}