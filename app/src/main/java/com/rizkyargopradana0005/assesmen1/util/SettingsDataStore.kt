package com.rizkyargopradana0005.assesmen1.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = "settings_preferences"
)


class SettingsDataStore(private val context: Context) {



    companion object {
        private val IS_LIST = booleanPreferencesKey("is_list")
        private val THEME_COLOR_KEY = stringPreferencesKey("theme_color")
        private val EMAIL_KEY = stringPreferencesKey("user_email")
    }
    val emailFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[EMAIL_KEY] }
    val layoutFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LIST] ?: true
    }

    suspend fun saveLayout(isList: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LIST] = isList
        }
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
        }
    }
        val themeColorFlow: Flow<String> = context.dataStore.data.map { preferences ->
            preferences[THEME_COLOR_KEY] ?: "#1FC41F"
        }

        suspend fun saveThemeColor(colorHex: String) {
            context.dataStore.edit { preferences ->
                preferences[THEME_COLOR_KEY] = colorHex
        }
    }
}