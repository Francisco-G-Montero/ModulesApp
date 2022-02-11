package com.frommetoyou.modulesapp2.domain.usecases

import android.app.Activity
import android.net.Uri
import android.util.Log
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class GetDynamicLinkUseCase @Inject constructor() {
    fun getSelectedBtnIfAvailable(activity: Activity, callback: (String) -> Unit) {
        var btnSelected: String = ""
        Firebase.dynamicLinks
            .getDynamicLink(activity.intent)
            .addOnSuccessListener(activity) { pendingDynamicLinkData ->
                // Get deep link from result (may be null if no link is found)
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                    deepLink?.let {
                        btnSelected = deepLink.getQueryParameter("btn") ?: "default"
                        callback(btnSelected)
                    }
                }
            }
            .addOnFailureListener(activity) { e ->
                Log.w("TAG", "getDynamicLink:onFailure", e)
            }
    }
}