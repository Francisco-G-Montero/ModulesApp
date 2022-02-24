package com.frommetoyou.modulesapp2.domain.usecases

import android.app.Activity
import android.net.Uri
import android.util.Log
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDynamicLinkDataUseCase @Inject constructor() {
    @ExperimentalCoroutinesApi
    fun getSelectedBtnDataIfAvailable(activity: Activity) = callbackFlow {
        var btnSelected = ""
        Firebase.dynamicLinks
            .getDynamicLink(activity.intent)
            .addOnSuccessListener(activity) { pendingDynamicLinkData ->
                // Get deep link from result (may be null if no link is found)
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                    deepLink?.let {
                        btnSelected = deepLink.getQueryParameter("btn") ?: "default"
                    }
                }
                if (btnSelected.isNotBlank()) trySend(btnSelected)
            }
            .addOnFailureListener(activity) { e ->
                Log.w("TAG", "getDynamicLink:onFailure", e)
                trySend("getDynamicLink:onFailure: $e")
            }
        awaitClose { this.cancel() }
    }
}