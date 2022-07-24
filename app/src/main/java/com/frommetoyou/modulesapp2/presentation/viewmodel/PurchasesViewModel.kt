package com.frommetoyou.modulesapp2.presentation.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.*
import com.frommetoyou.modulesapp2.data.util.BillingConnectionManager
import com.frommetoyou.modulesapp2.domain.BillingListenerHolder
import com.frommetoyou.modulesapp2.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchasesViewModel @Inject constructor(
    val queryProductsUseCase: QueryProductsUseCase,
    val queryPurchaseHistoryUseCase: QueryPurchaseHistoryUseCase,
    val acknowledgePurchaseUseCase: AcknowledgePurchaseUseCase,
    val launchBillingFlowUseCase: LaunchBillingFlowUseCase,
    val consumePurchaseUseCase: ConsumePurchaseUseCase,
    val billingConnectionManager: BillingConnectionManager,
    val billingInterface: BillingListenerHolder
) : ViewModel() {
    private val _skuDetailList = MutableLiveData<List<SkuDetails>>()
    val skuDetailList: LiveData<List<SkuDetails>>
        get() = _skuDetailList
    val purchaseList: LiveData<List<Purchase>>
        get() = billingInterface.purchaseList
    private val _onEkkoPurchaseConsumed = MutableLiveData<Boolean>()
    val onEkkoPurchaseConsumed: LiveData<Boolean>
        get() = _onEkkoPurchaseConsumed
    private val _onEkkoPurchaseAcknowledge = MutableLiveData<Boolean>()
    val onEkkoPurchaseAcknowledge: LiveData<Boolean>
        get() = _onEkkoPurchaseAcknowledge

    val onPurchaseUpdatedList: LiveData<List<Purchase>?> // TODO acknowledge purchase
        get() = billingInterface.onPurchaseUpdatedList

    /**
     * queryPurchaseHistoryUseCase:
     * Me conecto a la google play y una vez listo primero busco todas las compras del usuario
     * para ver si muestro los items mostrados en la UI como ya comprados o su cantidad
     *
     * queryProductsUseCase:
     * Al mismo tiempo busco todos los productos disponibles para comprar, esto con el fin de
     * proporcionar los skuDetails necesarios para que el user pueda clickear y proceder a una compra
     * */
    fun setupBilling() = viewModelScope.launch {
        billingConnectionManager.connectToGooglePlay {
            queryPurchaseHistoryUseCase()
            queryProductsUseCase { skuList ->
                _skuDetailList.postValue(skuList)
            }
        }
    }

    fun acknowledgePurchaseIfNecessary(purchase: Purchase) =
        viewModelScope.launch {
            acknowledgePurchaseUseCase(purchase).collect { billingResult ->
                _onEkkoPurchaseAcknowledge.value = true
            }
        }

    fun launchBillingFlow(skuDetail: SkuDetails, activity: Activity) {
        launchBillingFlowUseCase(skuDetail, activity)
    }

    fun consumeEkkoPurchase(purchase: Purchase) = viewModelScope.launch {
        consumePurchaseUseCase(purchase).collect { consumeResult ->
            consumeResult.purchaseToken?.let {
                _onEkkoPurchaseConsumed.value = true
            }
        }
    }
}
