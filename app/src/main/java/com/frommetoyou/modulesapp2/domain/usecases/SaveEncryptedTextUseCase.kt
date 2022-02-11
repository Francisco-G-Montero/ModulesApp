package com.frommetoyou.modulesapp2.domain.usecases

import android.content.Context
import android.content.SharedPreferences
import com.frommetoyou.modulesapp2.domain.utils.getEncrypedSharedPrefs
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val ENCRYPTED_TEXT = "encrypted_text"
const val SHARED_PREF_NAME = "secret_shared_prefs"

class SaveEncryptedTextUseCase @Inject constructor(
    @ApplicationContext val context: Context
){
    fun save(text: String){
        val sharedPreferences: SharedPreferences = getEncrypedSharedPrefs(context)
        val editor = sharedPreferences.edit()
        editor.putString(ENCRYPTED_TEXT, text).apply()
    }
}