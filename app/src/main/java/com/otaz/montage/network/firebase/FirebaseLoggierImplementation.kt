package com.otaz.montage.network.firebase

import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.crashlytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseLoggerImpl
@Inject
constructor() : FirebaseLogger {

    private val firebaseAnalytics = Firebase.analytics
    private val firebaseCrashlytics = Firebase.crashlytics

    override fun logFirebaseEvent(
        eventName: String,
        paramKey: String,
        paramValue: String
    ) {
        firebaseAnalytics.logEvent(eventName) {
            param(paramKey, paramValue)
        }
    }

    override fun logException(exception: Exception) {
        firebaseCrashlytics.recordException(exception)
    }

}