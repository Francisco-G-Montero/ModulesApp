package com.frommetoyou.modulesapp2.domain.repository

import android.app.Activity
import com.android.billingclient.api.*
import kotlinx.coroutines.flow.Flow

interface InAppPurchasesRepository {
    fun queryOneTimeProducts(callback: (skuDetails: List<SkuDetails>?) -> Unit)
    fun queryMyPurchasesHistory(listener: PurchasesResponseListener)
    fun launchBillingFlow(skuDetail: SkuDetails, activity: Activity)
    suspend fun acknowledgePurchase(purchase: Purchase): Flow<BillingResult>
    suspend fun consumePurchase(purchase: Purchase): Flow<ConsumeResult>
}