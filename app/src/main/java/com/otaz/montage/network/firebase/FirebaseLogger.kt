package com.otaz.montage.network.firebase

/**
 * https://github.com/AlexSheva-mason/Rick-Morty-Database/blob/master/app/src/main/java/com/shevaalex/android/rickmortydatabase/utils/firebase/FirebaseLogger.kt
 */
interface FirebaseLogger {

    fun logFirebaseEvent(
        eventName: String,
        paramKey: String,
        paramValue: String
    )

    fun logException(
        exception: Exception
    )

}