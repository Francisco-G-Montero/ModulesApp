package com.frommetoyou.modulesapp2.domain.usecases

import com.frommetoyou.modulesapp2.domain.BillingListenerHolder
import com.frommetoyou.modulesapp2.domain.repository.InAppPurchasesRepository
import javax.inject.Inject

/**
 * Se van a buscar todas las compras en el historial del user, esto devuelve una lista de
 * compras (Purchase)
 * */
class QueryPurchaseHistoryUseCase @Inject constructor(
    val inAppPurchasesRepository: InAppPurchasesRepository,
    val billingListenerHolder: BillingListenerHolder
) {
    operator fun invoke() =
        inAppPurchasesRepository.queryMyPurchasesHistory(billingListenerHolder)
}
