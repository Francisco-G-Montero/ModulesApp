package com.frommetoyou.modulesapp2.domain.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.frommetoyou.modulesapp2.R
import com.frommetoyou.modulesapp2.domain.usecases.IMG_URL_LIST_NAME
import com.frommetoyou.modulesapp2.domain.usecases.SaveImageFileUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

@HiltWorker
class ImageDownloadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val saveImageFileUseCase: SaveImageFileUseCase,
    private val imageFlowUtil: ImageFlowUtil,
) : Worker(context, workerParameters) {

    private val notificationId: Int = 500
    private val notificationChannel: String = "demo"

    private val notificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun doWork(): Result {
        displayNotification("Please wait...", 3, 0)
        val imgPathList = inputData.getStringArray(IMG_URL_LIST_NAME)

        imgPathList?.toList()?.forEachIndexed() { index, url ->
            Thread.sleep(2000) // emulating network call.
            val file = downloadImage(index, url)
            imageFlowUtil.appendImgPath(file.absolutePath)
        }
        notificationManager.cancel(notificationId)
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        notificationManager.cancel(notificationId)
    }

    @SuppressLint("CheckResult")
    private fun downloadImage(index: Int, url: String): File {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
        val response = client.newCall(request).execute()
        val bitmap = BitmapFactory.decodeStream(response.body?.byteStream())
        displayNotification("Imagen ${index + 1}", 3, index + 1)
        return saveImageFileUseCase.saveBitmapToFile(bitmap)
    }

    private fun displayNotification(title: String, total: Int, current: Int) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannel,
                notificationChannel,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(false)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder =
            NotificationCompat.Builder(applicationContext, notificationChannel)

        val notificationLayout = RemoteViews(applicationContext.packageName, R.layout.custom_notif)
        notificationLayout.setImageViewResource(R.id.img_notif, R.drawable.ic_cloud_download)
        notificationLayout.setTextViewText(
            R.id.tv_notif_progress,
            "$title ($current/$total complete)"
        )
        notificationLayout.setTextViewText(R.id.tv_notif_title, "Downloading Images")
        notificationLayout.setProgressBar(R.id.pb_notif, total, current, false)

        notificationBuilder
            .setContent(notificationLayout)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setSmallIcon(R.drawable.ic_sync)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
