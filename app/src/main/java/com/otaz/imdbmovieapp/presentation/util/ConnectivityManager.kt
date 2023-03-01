//package com.otaz.imdbmovieapp.presentation.util
//
//import android.app.Application
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.LifecycleOwner
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class ConnectivityManager @Inject constructor(
//    application: Application
//){
//    private val connectionLiveData = ConnectionLiveData(application)
//
//    // Specifically, this mutable state object [isNetworkAvailable] is being observed in the UI aka
//    // MovieDetailScreen and MovieListScreen. The value is being held here and passed into the
//    // onCreate override function where it is passed into the UI through the compose navigation
//
//    val isNetworkAvailable = mutableStateOf(false)
//
//    fun registerConnectionObserver(lifecycleOwner: LifecycleOwner){
//        connectionLiveData.observe(lifecycleOwner) { isConnected ->
//            isConnected?.let { isNetworkAvailable.value = it }
//        }
//    }
//
//    fun unregisterConnectionObserver(lifecycleOwner: LifecycleOwner){
//        connectionLiveData.removeObservers(lifecycleOwner)
//    }
//}