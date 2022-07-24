package com.frommetoyou.modulesapp2.domain.usecases

import android.content.Context
import android.widget.Toast
import androidx.work.*
import com.frommetoyou.modulesapp2.domain.utils.ImageDownloadWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

val imgUrlList = listOf(
    "https://blog.phonehouse.es/wp-content/uploads/2019/12/Android.jpg",
    "https://www.muycomputer.com/wp-content/uploads/2021/04/FLoC.png",
    "https://cdn-images-1.medium.com/max/960/1*K-jWMXQbAK98EdkuuaZCFg.png"
)

const val IMG_URL_LIST_NAME = "img_url_list"

class WorkManagerUseCase @Inject constructor(
    @ApplicationContext val context: Context
) {
    fun startWorker() {
        val data = Data.Builder()
            .putStringArray(IMG_URL_LIST_NAME, imgUrlList.toTypedArray())
            .build()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
        val oneTimeRequest = OneTimeWorkRequest.Builder(ImageDownloadWorker::class.java)
            .setInputData(data)
            .setConstraints(constraints.build())
            .addTag("demo")
            .build()
        Toast.makeText(context, "Starting worker", Toast.LENGTH_SHORT).show()
        WorkManager.getInstance(context)
            .enqueueUniqueWork("WorkTutorial", ExistingWorkPolicy.KEEP, oneTimeRequest)
    }
}
