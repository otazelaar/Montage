package com.otaz.montage.presentation

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.otaz.montage.interactors.app.DoesNetworkHaveInternet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val TAG = "C-Manager"

/**
 * Save all available networks with an internet connection to a set (@validNetworks).
 * As long as the size of the set > 0, this LiveData emits true.
 * MinSdk = 21.
 *
 * This LiveData class outputs a Boolean and denotes whether or not we have overall
 * internet connectivity in the device. This class is instantiated inside of an app wide singleton class
 * that way it can be injected into ViewModels, activities, use cases, etc.
 *
 * Inspired by:
 * https://github.com/AlexSheva-mason/Rick-Morty-Database/blob/master/app/src/main/java/com/shevaalex/android/rickmortydatabase/utils/networking/ConnectionLiveData.kt
 */
class ConnectionLiveData(private val context: Context) : LiveData<Boolean>() {

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onActive() {
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        networkCallback = createNetworkCallback()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(networkCallback)
        }
    }

    override fun onInactive() {
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            /* only starting from version Build.VERSION_CODES.O onAvailable() will always immediately
             be followed by a call to onCapabilitiesChanged.
             On versions below Build.VERSION_CODES.O when app is started with internet connection
             nothing apart from onAvailable() is being called, thus we need to pass postValue(true)
             here (although in some cases it could be false positive)
             */
            postValue(true)
        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            val isInternet = networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET)
            val isValidated = networkCapabilities.hasCapability(NET_CAPABILITY_VALIDATED)
            postValue(isInternet && isValidated)
        }

        override fun onLost(network: Network) {
            postValue(false)
        }

    }

}