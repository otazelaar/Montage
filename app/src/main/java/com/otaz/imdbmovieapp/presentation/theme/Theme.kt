package com.otaz.imdbmovieapp.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.otaz.imdbmovieapp.presentation.components.CircularIndeterminateProgressBar
import com.otaz.imdbmovieapp.presentation.components.DefaultSnackbar
import com.otaz.imdbmovieapp.presentation.components.GenericDialog
import com.otaz.imdbmovieapp.presentation.components.GenericDialogInfo
import java.util.*

private val LightThemeColors = lightColors(
    primary = Yellow600,
    primaryVariant = Yellow400,
    onPrimary = Black2,
    secondary = Color.White,
    secondaryVariant = Teal300,
    onSecondary = Color.Black,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey1,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
)

private val DarkThemeColors = darkColors(
    primary = Yellow700,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = Black1,
    onSecondary = Color.White,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = Black1,
    onSurface = Color.White,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
//    isNetworkAvailable : Boolean,
    displayProgressBar: Boolean,
    scaffoldState: ScaffoldState,
    dialogQueue: Queue<GenericDialogInfo>,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = QuickSandTypography,
        shapes = AppShapes
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.background(MaterialTheme.colors.background)
            ){
//                ConnectivityMonitor(isNetworkAvailable = isNetworkAvailable)
                content()
            }
            CircularIndeterminateProgressBar(isDisplayed = displayProgressBar)
            DefaultSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
                modifier = Modifier.align(
                    alignment = Alignment.BottomCenter
                )
            )
            ProcessDialogQueue(dialogQueue = dialogQueue)
        }
    }
}

@Composable
fun ProcessDialogQueue(
    dialogQueue: Queue<GenericDialogInfo>?,
) {
    dialogQueue?.peek()?.let { dialogInfo ->
        GenericDialog(
            onDismiss = dialogInfo.onDismiss,
            title = dialogInfo.title,
            description = dialogInfo.description,
            positiveAction = dialogInfo.positiveAction,
            negativeAction = dialogInfo.negativeAction
        )
    }
}
