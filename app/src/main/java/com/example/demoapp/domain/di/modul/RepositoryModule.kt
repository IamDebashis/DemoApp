package com.example.demoapp.domain.di.modul

import com.example.demoapp.data.api.NetworkService
import com.example.demoapp.data.repository.UserRepositoryImpl
import com.example.demoapp.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideUserRepository(networkService: NetworkService): UserRepository =
        UserRepositoryImpl(networkService)
}