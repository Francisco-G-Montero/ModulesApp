package com.frommetoyou.modulesapp2.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.billingclient.api.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillingListenerHolder @Inject constructor() : PurchasesResponseListener, PurchasesUpdatedListener {
    private val _purchaseList = MutableLiveData<List<Purchase>>()
    val purchaseList: LiveData<List<Purchase>> = _purchaseList

    private val _onPurchaseUpdatedList = MutableLiveData<List<Purchase>?>()
    val onPurchaseUpdatedList: LiveData<List<Purchase>?> = _onPurchaseUpdatedList

    override fun onQueryPurchasesResponse(billingResult: BillingResult, purchaseList: MutableList<Purchase>) {
        _purchaseList.postValue(purchaseList)
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            _onPurchaseUpdatedList.postValue(purchases)
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    }
}
