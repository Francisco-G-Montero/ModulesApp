package com.frommetoyou.modulesapp2.presentation.di

import com.frommetoyou.modulesapp2.data.util.CoroutinesDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {
    @Singleton
    @Provides
    fun provideCoroutinesDispatchers(): CoroutinesDispatcherProvider {
        return CoroutinesDispatcherProvider()
    }
}