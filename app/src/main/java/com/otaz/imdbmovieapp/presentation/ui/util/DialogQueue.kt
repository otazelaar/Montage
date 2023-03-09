package com.otaz.imdbmovieapp.presentation.ui.util

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.otaz.imdbmovieapp.presentation.components.GenericDialogInfo
import com.otaz.imdbmovieapp.presentation.components.PositiveAction
import java.util.*

/**
 * DialogQueue handles queue of errors in a "First-In-First-Out" order
 */

class DialogQueue {

    @SuppressLint("MutableCollectionMutableState")
    val queue: MutableState<Queue<GenericDialogInfo>> = mutableStateOf(LinkedList())

    private fun removeHeadMessage(){
        if (queue.value.isNotEmpty()) {
            val update = queue.value
            // The following line will remove the first (oldest message)
            update.remove()
            // The next line of code is used force the list to recompose.
            // Calling update.remove() absent of the following code will update the list
            // without causing it to recompose.
            queue.value = ArrayDeque()
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