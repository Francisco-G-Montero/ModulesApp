package com.frommetoyou.modulesapp2.presentation.di

import android.content.Context
import com.example.feature_lists.domain.repository.AppResourcesRepository
import com.frommetoyou.modulesapp2.data.services.local.AppResourcesRepositoryImpl
import com.frommetoyou.modulesapp2.data.util.CoroutinesDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideAppResourcesRepository(@ApplicationContext context: Context): AppResourcesRepository {
        return AppResourcesRepositoryImpl(context)
    }
}
