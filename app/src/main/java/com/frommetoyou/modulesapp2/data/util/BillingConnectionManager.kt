package com.frommetoyou.modulesapp2.data.util

import android.util.Log
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import javax.inject.Inject

const val BILLING_TAG = "billing_tag"

/**
 * El billing es provisto por el di
 * Se conecta a la google play antes de realizar cualquier tipo de operacion
 * */
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
