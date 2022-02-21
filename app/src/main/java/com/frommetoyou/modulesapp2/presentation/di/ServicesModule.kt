package com.frommetoyou.modulesapp2.presentation.di

import android.content.Context
import com.android.billingclient.api.BillingClient
import com.frommetoyou.modulesapp2.domain.BillingListenerHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {
    @Singleton
    @Provides
    fun provideBillingClient(
        @ApplicationContext context: Context,
        billingInterface: BillingListenerHolder
    ): BillingClient {
        return BillingClient
            .newBuilder(context)
            .setListener(billingInterface)
            .enablePendingPurchases()
            .build()
    }
}