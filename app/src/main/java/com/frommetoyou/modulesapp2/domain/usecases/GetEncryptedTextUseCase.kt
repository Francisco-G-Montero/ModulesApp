package com.frommetoyou.modulesapp2.domain.usecases

import android.content.Context
import com.frommetoyou.modulesapp2.domain.utils.getEncrypedSharedPrefs
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val DEFAULT_TEXT = "no text"

class GetEncryptedTextUseCase @Inject constructor(
    @ApplicationContext val context: Context
) {
    fun getEncryptedText(): String {
        val sharedPreferences = getEncrypedSharedPrefs(context)
        val text = sharedPreferences.getString(ENCRYPTED_TEXT, DEFAULT_TEXT)
        return text?: DEFAULT_TEXT
    }
}