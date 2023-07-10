package com.otaz.montage.presentation.ui.movie_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun MovieListSearchBar(
    expression: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    resetForNextSearch: () -> Unit,
){
    TextField(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 8.dp, start = 4.dp, end = 4.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = MaterialTheme.colors.surface,
        ),
        value = expression,
        onValueChange = { userInput ->
            onQueryChanged(userInput)
        },
        placeholder = {
            Text(text = "Search Movies")
        },
        keyboardOptions =
        KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                modifier = Modifier.clickable {
                    resetForNextSearch()
                    focusRequester.requestFocus()
                }
            )
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                onExecuteSearch()
                focusManager.clearFocus()
            },
        ),
        textStyle = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.onSurface),
    )
}