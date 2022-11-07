package com.example.moviesearchapp.view.component.favorite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.domain.model.MovieInfoModel
import com.example.moviesearchapp.R
import com.example.moviesearchapp.view.utils.htmlToString
import com.example.moviesearchapp.view.utils.onSingleClick
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

@Composable
fun FavoriteEditModeView(
    isEdit: Boolean,
    isSelectedAll: Boolean,
    onEditModeClick: () -> Unit,
    onSelectAllClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.str_edit),
            modifier = Modifier
                .padding(end = 5.dp)
                .onSingleClick {
                    onEditModeClick()
                },
            style = TextStyle(
                fontWeight = if (isEdit) FontWeight.Bold else FontWeight.Medium,
                color = if (!isEdit) Color.LightGray else Color.Black
            ),
        )

        Divider(modifier = Modifier
            .height(15.dp)
            .width(1.dp))

        Text(
            text = stringResource(id = R.string.str_all_select),
            modifier = Modifier
                .padding(start = 5.dp)
                .onSingleClick {
                    onSelectAllClick()
                },
            style = TextStyle(
                fontWeight = if (isSelectedAll) FontWeight.Bold else FontWeight.Medium,
                color = if (!isSelectedAll) Color.LightGray else Color.Black
            )
        )
    }
}

@Composable
fun FavoriteMovieItemView(
    modifier: Modifier = Modifier,
    movieInfoModel: MovieInfoModel,
    isEdit: Boolean,
    isSelected: Boolean,
    onRootClick: () -> Unit,
    onSelectClick: (isSelected: Boolean) -> Unit
) {

    Row(modifier = modifier
        .onSingleClick {
            onRootClick()
        }
        .padding(10.dp)
        .fillMaxWidth()
        .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(
            visible = !isEdit,
            enter = slideInHorizontally(initialOffsetX = { -it }),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(movieInfoModel.image)
                    .crossfade(true).build(),
                placeholder = painterResource(id = R.drawable.search_none),
                error = painterResource(id = R.drawable.search_none),
                contentDescription = "",
                modifier = modifier.size(90.dp),
                contentScale = ContentScale.Fit
            )
        }

        AnimatedVisibility(
            visible = isEdit,
            enter = slideInHorizontally(initialOffsetX = { -it }),
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .onSingleClick {
                        onSelectClick(isSelected)
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = if (!isSelected)
                            R.drawable.checkbox_01_off
                        else
                            R.drawable.checkbox_01_on
                    ),
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
        ) {
            Column {
                Text(
                    text = movieInfoModel.title.htmlToString(), style = TextStyle(
                        fontWeight = FontWeight.Bold, fontSize = 14.sp
                    )
                )

                RatingBar(value = movieInfoModel.rating,
                    config = RatingBarConfig().activeColor(colorResource(id = R.color.orange))
                        .inactiveColor(Color.LightGray).stepSize(StepSize.HALF).numStars(5)
                        .isIndicator(true).size(13.dp).style(RatingBarStyle.HighLighted),
                    onValueChange = {},
                    onRatingChanged = {})

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
                    }, fontSize = 13.sp
                )
            }
        }
    }

    Divider(modifier = Modifier.fillMaxWidth())
}