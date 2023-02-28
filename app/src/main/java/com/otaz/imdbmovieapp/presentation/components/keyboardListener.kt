package com.otaz.imdbmovieapp.presentation.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalDensity

//    The goal of the following function is to verify when the keyboard is or is not present and
//    to get rid of the blinking text indicator as a result.
@Composable
fun keyboardListener(): Boolean{
    val isVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    var keyboardIsVisible = false

    LaunchedEffect(key1 = isVisible) {
        if (isVisible) {
            keyboardIsVisible = true
        }
    }
    return keyboardIsVisible
}