package com.frommetoyou.modulesapp2.domain.usecases

import android.content.Context
import android.os.Environment
import android.util.Log
import com.frommetoyou.modulesapp2.domain.utils.ImageFlowUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

const val PICTURES_PATH =
    "/storage/emulated/0/Android/data/com.frommetoyou.modulesapp2/files/Pictures/"

class GetStoredImgUseCase @Inject constructor(
    private val imageFlowUtil: ImageFlowUtil,
    @ApplicationContext val context: Context
) {
    fun fetchImages() {
        val files = File(PICTURES_PATH).listFiles()
        val fileNames = mutableListOf<String>()
        files?.forEach { file ->
            fileNames.add(file.name)
        }
        fileNames.forEach { filename ->
            imageFlowUtil.appendImgPath(PICTURES_PATH + filename)
        }
    }
}