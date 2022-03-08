package com.frommetoyou.modulesapp2.presentation.di

import com.android.billingclient.api.BillingClient
import com.frommetoyou.modulesapp2.data.repository.FactsRepositoryImpl
import com.frommetoyou.modulesapp2.data.repository.InAppPurchasesImpl
import com.frommetoyou.modulesapp2.data.services.api.FactsApiService
import com.frommetoyou.modulesapp2.data.util.CoroutinesDispatcherProvider
import com.frommetoyou.modulesapp2.domain.repository.FactsRepository
import com.frommetoyou.modulesapp2.domain.repository.InAppPurchasesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {
    @Singleton
    @Provides
    fun provideInAppPurchasesRepository(
        billingClient: BillingClient,
        coroutinesDispatcherProvider: CoroutinesDispatcherProvider
    ): InAppPurchasesRepository {
        return InAppPurchasesImpl(billingClient, coroutinesDispatcherProvider)
    }
    @Singleton
    @Provides
    fun provideFactsRepository(
        factsApiService: FactsApiService
    ): FactsRepository {
        return FactsRepositoryImpl(factsApiService)
    }
}
