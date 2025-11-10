package com.bankingapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bankingapp.data.model.User
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "banking_preferences")

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    private val dataStore = context.dataStore

    companion object {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val USER_DATA = stringPreferencesKey("user_data")
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val HAS_PIN = booleanPreferencesKey("has_pin")
    }

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    val isDarkMode: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_DARK_MODE] ?: false
    }

    val currentUser: Flow<User?> = dataStore.data.map { preferences ->
        preferences[USER_DATA]?.let { json ->
            gson.fromJson(json, User::class.java)
        }
    }

    val hasPin: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[HAS_PIN] ?: false
    }

    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[USER_DATA] = gson.toJson(user)
            preferences[IS_LOGGED_IN] = true
            preferences[HAS_PIN] = user.pin != null
        }
    }

    suspend fun updateUser(user: User) {
        saveUser(user)
    }

    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = enabled
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
