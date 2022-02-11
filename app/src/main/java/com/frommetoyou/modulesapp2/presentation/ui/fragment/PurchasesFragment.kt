package com.frommetoyou.modulesapp2.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.billingclient.api.*
import com.bumptech.glide.Glide
import com.frommetoyou.modulesapp2.databinding.FragmentPurchasesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PurchasesFragment : Fragment() {

    private lateinit var binding: FragmentPurchasesBinding

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            // To be implemented in a later section.
        }

    private var billingClient = BillingClient.newBuilder(requireContext())
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases()
        .build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPurchasesBinding.inflate(layoutInflater)
        Glide.with(requireContext())
            .load("https://pbs.twimg.com/media/CvFXqKwWEAAZdlV.jpg")
            .into(binding.imgIori)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* binding.btnBuyIori.setOnClickListener {
            val skuDetails = null
            val flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build()
            val responseCode = billingClient.launchBillingFlow(requireActivity(), flowParams).responseCode
        }
        connectToGooglePlay()*/
    }

    private fun connectToGooglePlay() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode ==  BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                }
            }
            override fun onBillingServiceDisconnected() {
                //connectToGooglePlay() me vuelvo a conectar pero primero tengo que testear de no crear un loop inf
                Toast.makeText(requireContext(), "Hubo un problema al iniciar la conexion con Google Play", Toast.LENGTH_SHORT).show()
            }
        })
    }

    suspend fun querySkuDetails() {
        val skuList = ArrayList<String>()
        skuList.add("premium_upgrade")
        skuList.add("gas")
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)

        // leverage querySkuDetails Kotlin extension function
        val skuDetailsResult = withContext(Dispatchers.IO) {
            billingClient.querySkuDetails(params.build())
        }
        // Process the result.
    }

    /*override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    }*/
}