package com.bankingapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bankingapp.data.local.dao.UserDao
import com.bankingapp.data.local.toEntity
import com.bankingapp.data.local.toUser
import com.bankingapp.data.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "banking_preferences")

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userDao: UserDao
) {
    private val dataStore = context.dataStore

    companion object {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val CURRENT_USER_ID = stringPreferencesKey("current_user_id")
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val HAS_PIN = booleanPreferencesKey("has_pin")
    }

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    val isDarkMode: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_DARK_MODE] ?: false
    }

    // Get current user from Room database
    val currentUser: Flow<User?> = userDao.getCurrentUser().map { it?.toUser() }

    val hasPin: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[HAS_PIN] ?: false
    }

    suspend fun saveUser(user: User) {
        // Save to Room database
        userDao.insertUser(user.toEntity())

        // Save user ID and login state to DataStore
        dataStore.edit { preferences ->
            preferences[CURRENT_USER_ID] = user.id
            preferences[IS_LOGGED_IN] = true
            preferences[HAS_PIN] = user.pin != null
        }
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user.toEntity())
        dataStore.edit { preferences ->
            preferences[HAS_PIN] = user.pin != null
        }
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
        // Note: We keep the user data in Room for future logins
    }
}
