package com.frommetoyou.modulesapp2.data.services.local

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.feature_lists.domain.repository.AppResourcesRepository
import com.frommetoyou.modulesapp2.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppResourcesRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context
) : AppResourcesRepository {
    override fun getEkkoImageResource(): Bitmap? {
        return ContextCompat.getDrawable(context, R.drawable.img_ekko_sticker)?.toBitmap()
    }
}
