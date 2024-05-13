package com.example.demoapp.domain.di.modul

import com.example.demoapp.data.api.NetworkSeriveImpl
import com.example.demoapp.data.api.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService = NetworkSeriveImpl()



}