package com.frommetoyou.modulesapp2.data.util

import android.util.Log
import android.widget.Toast
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import javax.inject.Inject

const val BILLING_TAG = "billing_tag"

class BillingConnectionManager @Inject constructor(
    val billingClient: BillingClient
) {
    fun connectToGooglePlay(callback: () -> Unit) {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    callback()
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.v(BILLING_TAG, "Hubo un problema al iniciar la conexion con Google Play")
            }
        })
    }
}