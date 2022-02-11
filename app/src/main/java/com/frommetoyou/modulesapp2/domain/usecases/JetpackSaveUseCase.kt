package com.frommetoyou.modulesapp2.domain.usecases

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val USER_TEXT = stringPreferencesKey("userText")

class JetpackSaveUseCase @Inject constructor(
    @ApplicationContext val context: Context
) {
    suspend fun save(text: String) {
        context.dataStore.edit { settings ->
            val userText = settings[USER_TEXT] ?: text
            settings[USER_TEXT] = userText
        }
    }

    fun getUserText(): Flow<String> {
        val getFlow = flow {
            context.dataStore.data
                .map { preferences ->
                    preferences[USER_TEXT] ?: "null"
                }.collect {
                    emit(it)
                }
        }
        return getFlow
    }
}