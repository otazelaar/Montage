package com.otaz.imdbmovieapp.presentation.ui.util

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.otaz.imdbmovieapp.presentation.components.GenericDialogInfo
import com.otaz.imdbmovieapp.presentation.components.PositiveAction
import java.util.*

class DialogQueue {

    // Queue for "First-In-First-Out" behavior
    val queue: MutableState<Queue<GenericDialogInfo>> = mutableStateOf(LinkedList())

    fun removeHeadMessage(){
        if (queue.value.isNotEmpty()) {
            val update = queue.value
            update.remove() // remove first (oldest message)
            queue.value = ArrayDeque() // this is here to force the list to recompose. update.remove() will update the list but it will not recompose the list. (this may be a bug?)
            queue.value = update
        }
    }

    fun appendErrorMessage(title: String, description: String){
        queue.value.offer(
            GenericDialogInfo.Builder()
                .title(title)
                .onDismiss(this::removeHeadMessage)
                .description(description)
                .positive(
                    PositiveAction(
                        positiveBtnText = "Ok",
                        onPositiveAction = this::removeHeadMessage,
                    )
                )
                .build()
        )
    }
}