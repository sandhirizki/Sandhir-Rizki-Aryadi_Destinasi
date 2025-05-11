package com.example.destinasi.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {
    companion object {
        val IS_LIST_VIEW_KEY = booleanPreferencesKey("is_list_view")
    }

    val preferenceFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_LIST_VIEW_KEY] ?: true
        }

    suspend fun saveLayoutPreference(isListView: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LIST_VIEW_KEY] = isListView
        }
    }
}