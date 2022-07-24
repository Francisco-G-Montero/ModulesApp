package com.frommetoyou.modulesapp2.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.billingclient.api.*
import com.frommetoyou.modulesapp2.data.util.BILLING_TAG
import com.frommetoyou.modulesapp2.databinding.FragmentPurchasesBinding
import com.frommetoyou.modulesapp2.presentation.extensions.loadUrlImage
import com.frommetoyou.modulesapp2.presentation.viewmodel.PurchasesViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

const val IORI_ITEM_ID = "iori_paint_id"
const val EKKO_ITEM_ID = "ekko_sticker"
const val IMG_IORI_URL = "https://pbs.twimg.com/media/CvFXqKwWEAAZdlV.jpg"
const val IAP_PRODUCT_ID = "productId"
const val IAP_ACKNOWLEDGED = "acknowledged"

@Singleton
@AndroidEntryPoint
class PurchasesFragment @Inject constructor() : Fragment() {
    private lateinit var binding: FragmentPurchasesBinding
    private val viewModel: PurchasesViewModel by viewModels()
    var ekkoCounter = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPurchasesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgIori.loadUrlImage(IMG_IORI_URL)
        viewModel.setupBilling()
        viewModel.skuDetailList.observe(viewLifecycleOwner) { skuDetailsList ->
            setupPurchaseButtonsListener(skuDetailsList)
        }
        viewModel.purchaseList.observe(viewLifecycleOwner) { purchaseList ->
            setupViewForPurchasedItems(purchaseList)
        }
        viewModel.onPurchaseUpdatedList.observe(viewLifecycleOwner) { updatedPurchases ->
            updatedPurchases?.let { setupViewForPurchasedItems(it) }
            updatedPurchases?.forEach {
                viewModel.acknowledgePurchaseIfNecessary(it)
            }
        }
        viewModel.onEkkoPurchaseConsumed.observe(viewLifecycleOwner) { wasConsumed ->
            handleEkkoConsumtion(wasConsumed)
        }
        viewModel.onEkkoPurchaseAcknowledge.observe(viewLifecycleOwner) { wasAcknowledged ->
            handleEkkoAcknowledgement(wasAcknowledged)
        }
    }

    private fun handleEkkoAcknowledgement(wasAcknowledged: Boolean) {
        if (wasAcknowledged) {
            binding.btnBuyEkko.isEnabled = false
            binding.btnConsumeEkko.isEnabled = true
            binding.tvEkkoStickerCounter.text = "${++ekkoCounter}/1"
        } else {
            binding.btnBuyEkko.isEnabled = true
            binding.btnConsumeEkko.isEnabled = false
            binding.tvEkkoStickerCounter.text = "${--ekkoCounter}/1"
        }
    }

    private fun handleEkkoConsumtion(wasConsumed: Boolean) {
        if (wasConsumed) {
            binding.tvEkkoStickerCounter.text = "${--ekkoCounter}/1"
            binding.btnConsumeEkko.isEnabled = false
            binding.btnBuyEkko.isEnabled = true
        }
    }

    private fun setupViewForPurchasedItems(purchaseList: List<Purchase>) {
        purchaseList.forEach { purchase ->
            Log.v(BILLING_TAG, purchase.originalJson)
            val json = JSONObject(purchase.originalJson)
            val prodId = json.getString("productId")
            val acknowledged = json.getBoolean(IAP_ACKNOWLEDGED)
            if (prodId == IORI_ITEM_ID) {
                binding.cbIoriBuyStatus.isChecked = true
            } else if (prodId == EKKO_ITEM_ID) {
                if (acknowledged) {
                    ekkoCounter += 1
                }
                binding.tvEkkoStickerCounter.text = "$ekkoCounter/1"
                binding.btnBuyEkko.isEnabled = !acknowledged
                binding.btnConsumeEkko.isEnabled = acknowledged
                binding.btnConsumeEkko.setOnClickListener {
                    viewModel.consumeEkkoPurchase(purchase)
                }
            }
            Log.v(BILLING_TAG, "compra::$purchase")
        }
    }

    private fun setupPurchaseButtonsListener(skuDetailsList: List<SkuDetails>) {
        for (skuDetail in skuDetailsList) {
            Log.v(BILLING_TAG, "sku list: $skuDetailsList")
            val json = JSONObject(skuDetail.originalJson)
            val productId = json.getString(IAP_PRODUCT_ID)
            if (productId == IORI_ITEM_ID) {
                binding.btnBuyIori.setOnClickListener {
                    viewModel.launchBillingFlow(skuDetail, requireActivity())
                }
            } else if (productId == EKKO_ITEM_ID) {
                binding.btnBuyEkko.setOnClickListener {
                    viewModel.launchBillingFlow(skuDetail, requireActivity())
                }
            }
            Log.v(BILLING_TAG, skuDetail.toString())
        }
    }
}
