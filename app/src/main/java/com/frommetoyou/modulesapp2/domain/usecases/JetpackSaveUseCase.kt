package com.frommetoyou.modulesapp2.domain.usecases

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.frommetoyou.modulesapp2.data.util.ActionResult
import com.frommetoyou.modulesapp2.data.util.NullModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val USER_TEXT = stringPreferencesKey("userText")

class JetpackSaveUseCase @Inject constructor(
    @ApplicationContext val context: Context
) {
    suspend fun save(text: String) = flow {
        context.dataStore.edit { settings ->
            val userText = settings[USER_TEXT] ?: text
            settings[USER_TEXT] = userText
            emit(ActionResult.Success(NullModel()))
        }
    }

    fun getUserText(): Flow<ActionResult<String>> = flow {
        context.dataStore.data
            .map { preferences ->
                preferences[USER_TEXT] ?: "null"
            }.collect {
                emit(ActionResult.Success(it))
            }
    }
}