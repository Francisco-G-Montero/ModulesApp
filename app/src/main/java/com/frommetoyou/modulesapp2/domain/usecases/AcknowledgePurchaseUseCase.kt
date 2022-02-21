package com.frommetoyou.modulesapp2.domain.usecases

import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.frommetoyou.modulesapp2.domain.repository.InAppPurchasesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AcknowledgePurchaseUseCase @Inject constructor(
    val inAppPurchasesRepository: InAppPurchasesRepository
) {
    suspend operator fun invoke(purchase: Purchase): Flow<BillingResult> {
        return inAppPurchasesRepository.acknowledgePurchase(purchase)
    }
}