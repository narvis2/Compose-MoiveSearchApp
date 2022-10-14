package com.example.moviesearchapp.view.component.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.moviesearchapp.R

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
        modifier = modifier.fillMaxWidth().padding(top = 10.dp, start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = modifier
                .wrapContentWidth()
                .padding(end = 5.dp),
            value = searchValue,
            onValueChange = onChangeSearchValue,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.orange),
                unfocusedBorderColor = colorResource(id = R.color.white),
                focusedLabelColor =  colorResource(id = R.color.orange),
                errorBorderColor = colorResource(id = R.color.red),
                errorLabelColor = colorResource(id = R.color.red)
            ),
            isError = searchValue.length < 2,
            singleLine = true,
            trailingIcon = {
                if (searchValue.isNotEmpty()) {
                    IconButton(onClick = { onResetSearchButton() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "close", tint = colorResource(R.color.orange),)
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
            onClick = { onSearchButtonClick() },
            border = BorderStroke(
                1.dp,
                if (searchValue.length < 2) colorResource(id = R.color.red) else colorResource(id = R.color.orange)
            ),
            enabled = searchValue.isNotEmpty()
        ) {
            Text(
                text = "검색",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    if (searchValue.length < 2) colorResource(id = R.color.red) else colorResource(id = R.color.orange)
                )
            )
        }
    }
}