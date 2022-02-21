package com.frommetoyou.modulesapp2.domain.usecases

import com.android.billingclient.api.PurchasesResponseListener
import com.frommetoyou.modulesapp2.domain.BillingListenerHolder
import com.frommetoyou.modulesapp2.domain.repository.InAppPurchasesRepository
import javax.inject.Inject

class QueryPurchaseHistoryUseCase @Inject constructor(
    val inAppPurchasesRepository: InAppPurchasesRepository,
    val billingListenerHolder: BillingListenerHolder
) {
    operator fun invoke() =
        inAppPurchasesRepository.queryMyPurchasesHistory(billingListenerHolder)
}