package com.otaz.montage.presentation

import androidx.lifecycle.MutableLiveData

/**
 * Author Phillip Lackner: https://www.youtube.com/watch?v=TzV0oCRDNfM
 */
interface ConnectivityManager {

    val isNetworkAvailable: MutableLiveData<Boolean>

    fun registerConnectionObserver()

    fun unregisterConnectionObserver()
}