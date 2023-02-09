package com.otaz.imdbmovieapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.otaz.imdbmovieapp.R


@Composable
fun ConnectivityMonitor(
    isNetworkAvailable: Boolean,
){
    if(!isNetworkAvailable){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Row {
                Icon(
                    contentDescription = "No network connection icon",
                    painter = painterResource(R.drawable.ic_network_connection_unavailable),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(8.dp),
                    tint = MaterialTheme.colors.primary,
                )
                Text(
                    text = "No network connection",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(8.dp),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary,
                )
            }
        }
    }
}