package com.yosa.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceDataStore private constructor(private val dataStore: DataStore<Preferences>) {

    fun getOnboard(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[ONBOARD_KEY] ?: false
        }
    }

    suspend fun saveOnboard(save: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARD_KEY] = save
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PreferenceDataStore? = null
        private val ONBOARD_KEY = booleanPreferencesKey("onBoard")

        fun getInstance(dataStore: DataStore<Preferences>): PreferenceDataStore {
            return INSTANCE ?: synchronized(this) {
                val instance = PreferenceDataStore(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}