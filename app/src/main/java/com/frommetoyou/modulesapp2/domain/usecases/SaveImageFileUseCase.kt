package com.frommetoyou.modulesapp2.domain.usecases

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import javax.inject.Inject

const val FILE_PREFIX = "IMG"
const val FILE_SUFFIX = ".jpg"

class SaveImageFileUseCase @Inject constructor(
    @ApplicationContext val context: Context
) {
    @Throws(IOException::class)
    fun saveBitmapToFile(bitmap: Bitmap): File {
        // Create an image file name
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "${FILE_PREFIX}_",
            FILE_SUFFIX, /* suffix */
            storageDir /* directory */
        ).apply {
            val fOut = FileOutputStream(this)
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut)
            fOut.flush()
            fOut.close()
        }
    }
}
