//package com.otaz.montage.presentation.ui
//
//import android.content.Context
//import android.net.ConnectivityManager
//import android.net.Network
//import android.os.Build
//import androidx.annotation.RequiresApi
//import com.otaz.montage.presentation.ConnectivityManager
//import kotlinx.coroutines.channels.awaitClose
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.callbackFlow
//import kotlinx.coroutines.flow.distinctUntilChanged
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//import javax.inject.Singleton
//
///**
// * Author Phillip Lackner: https://www.youtube.com/watch?v=TzV0oCRDNfM
// */
//@Singleton
//class NetworkConnectivityManager
//@Inject
//constructor(
//    context: Context
//): com.otaz.montage.presentation.ConnectivityManager {
//
//    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//    @RequiresApi(Build.VERSION_CODES.N)
//    override fun observe(): Flow<com.otaz.montage.presentation.ConnectivityObserver.ConnectivityManager.Status> {
//        return callbackFlow {
//            val callback = object: ConnectivityManager.NetworkCallback() {
//                override fun onAvailable(network: Network) {
//                    super.onAvailable(network)
//                    launch { send(ConnectivityManager.Status.Available) }
//                }
//
//                override fun onLosing(network: Network, maxMsToLive: Int) {
//                    super.onLosing(network, maxMsToLive)
//                    launch { send(ConnectivityManager.Status.Losing) }
//
//                }
//
//                override fun onLost(network: Network) {
//                    super.onLost(network)
//                    launch { send(ConnectivityManager.Status.Lost) }
//
//                }
//
//                override fun onUnavailable() {
//                    super.onUnavailable()
//                    launch { send(ConnectivityManager.Status.Unavailable) }
//
//                }
//            }
//
//            connectivityManager.registerDefaultNetworkCallback(callback)
//            awaitClose {
//                connectivityManager.unregisterNetworkCallback(callback)
//            }
//        }.distinctUntilChanged()
//    }
//}