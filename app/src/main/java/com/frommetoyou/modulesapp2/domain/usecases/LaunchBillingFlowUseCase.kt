package com.frommetoyou.modulesapp2.domain.usecases

import android.app.Activity
import com.android.billingclient.api.SkuDetails
import com.frommetoyou.modulesapp2.domain.repository.InAppPurchasesRepository
import javax.inject.Inject

class LaunchBillingFlowUseCase @Inject constructor(
    val inAppPurchasesRepository: InAppPurchasesRepository
) {
    operator fun invoke(skuDetail: SkuDetails, activity: Activity) {
        inAppPurchasesRepository.launchBillingFlow(skuDetail, activity)
    }
}