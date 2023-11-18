package com.otaz.montage.di

import com.otaz.montage.presentation.ConnectivityManager
import com.otaz.montage.presentation.ui.ConnectivityManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ImplBindingModule {

    @Binds
    fun bindConnectivityManager(connectivityManagerImpl: ConnectivityManagerImpl): ConnectivityManager

}