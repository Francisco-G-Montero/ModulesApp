package com.frommetoyou.modulesapp2.domain.usecases

import com.android.billingclient.api.SkuDetails
import com.frommetoyou.modulesapp2.domain.repository.InAppPurchasesRepository
import javax.inject.Inject

class QueryProductsUseCase @Inject constructor(
    val inAppPurchasesRepository: InAppPurchasesRepository
) {
    operator fun invoke(callback: (skuDetails: List<SkuDetails>?) -> Unit){
        inAppPurchasesRepository.queryOneTimeProducts{
            callback(it)
        }
    }
}