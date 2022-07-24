package com.frommetoyou.modulesapp2.data.repository

import android.app.Activity
import android.util.Log
import com.android.billingclient.api.*
import com.frommetoyou.modulesapp2.data.util.BILLING_TAG
import com.frommetoyou.modulesapp2.data.util.CoroutinesDispatcherProvider
import com.frommetoyou.modulesapp2.domain.repository.InAppPurchasesRepository
import com.frommetoyou.modulesapp2.presentation.ui.fragment.EKKO_ITEM_ID
import com.frommetoyou.modulesapp2.presentation.ui.fragment.IORI_ITEM_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InAppPurchasesImpl @Inject constructor(
    val billingClient: BillingClient,
    val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : InAppPurchasesRepository {
    override fun queryOneTimeProducts(callback: (skuDetails: List<SkuDetails>?) -> Unit) {
        val skuListToQuery = ArrayList<String>()
        skuListToQuery.add(IORI_ITEM_ID)
        skuListToQuery.add(EKKO_ITEM_ID)
        val params = SkuDetailsParams.newBuilder()
        params
            .setSkusList(skuListToQuery)
            .setType(BillingClient.SkuType.INAPP)

        billingClient.querySkuDetailsAsync(
            params.build()
        ) { result, skuDetails ->
            callback(skuDetails)
            Log.v(BILLING_TAG, "onSkuDetailsResponse ${result.responseCode}")
        }
    }

    override fun queryMyPurchasesHistory(listener: PurchasesResponseListener) {
        billingClient.queryPurchasesAsync(BillingClient.SkuType.INAPP, listener)
    }

    override fun launchBillingFlow(skuDetail: SkuDetails, activity: Activity) {
        val flowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetail)
            .build()
        billingClient.launchBillingFlow(
            activity,
            flowParams
        ).responseCode
    }

    override suspend fun acknowledgePurchase(purchase: Purchase): Flow<BillingResult> = flow {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                emit(billingClient.acknowledgePurchase(acknowledgePurchaseParams.build()))
            }
        }
    }

    override suspend fun consumePurchase(purchase: Purchase): Flow<ConsumeResult> = flow {
        val consumeParams =
            ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()
        emit(billingClient.consumePurchase(consumeParams))
    }
        .flowOn(coroutinesDispatcherProvider.io)
}
