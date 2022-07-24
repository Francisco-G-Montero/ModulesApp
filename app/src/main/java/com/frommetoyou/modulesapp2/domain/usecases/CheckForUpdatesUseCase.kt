package com.frommetoyou.modulesapp2.domain.usecases

import android.app.Activity
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability
import javax.inject.Inject

const val REQUEST_CODE = 1111

class CheckForUpdatesUseCase @Inject constructor() {
    fun check(activity: Activity) {
        val appUpdateManager = AppUpdateManagerFactory.create(activity)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                // To apply a flexible update instead, pass in AppUpdateType.FLEXIBLE
                appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    IMMEDIATE,
                    activity,
                    REQUEST_CODE
                )
            }
        }
    }
}
