package com.otaz.montage.presentation.components.movie_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MovieCategoryChip(
    category: String,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
){
    Surface(
        modifier = Modifier
            .padding(end = 8.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(20.dp),
        color = if(isSelected) Color.Transparent else MaterialTheme.colors.primary,
        border = BorderStroke(width = 2.dp, brush = Brush.linearGradient(
            0.0f to Color.Red,
            0.3f to Color.Green,
            0.8f to Color.Blue,
            start = Offset(0.0f, 0.0f),
            end = Offset(0.0f, 100.0f),
        )),
    ) {
        Row(
            modifier = Modifier
                .toggleable(
                    value = isSelected,
                    enabled = !isSelected,
                    onValueChange = {
                        onSelectedCategoryChanged(category)
                        onExecuteSearch()
                    }
                )
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.body2,
                color = if(isSelected) Color.White else Color.Black,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}