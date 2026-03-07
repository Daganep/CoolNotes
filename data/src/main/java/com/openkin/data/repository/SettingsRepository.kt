package com.openkin.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.openkin.data.datastorekeys.CALENDAR_SELECTED_DAY
import com.openkin.data.datastorekeys.LAST_SELECTED_VIEW_TYPE
import com.openkin.data.datastorekeys.NOTIFY_FIRST_REQUEST
import com.openkin.data.mapper.localDateFromString
import com.openkin.domain.repository.ISettingsRepository
import com.openkin.domain.utils.DEFAULT_VIEW_TYPE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class SettingsRepository(
    private val stateDataStore: DataStore<Preferences>,
    private val settingsDataStore: DataStore<Preferences>,
) : ISettingsRepository {

    override suspend fun saveViewType(viewType: Int) {
        stateDataStore.edit { state -> state[LAST_SELECTED_VIEW_TYPE] = viewType }
    }

    override suspend fun getStoredViewType(): Flow<Int> =
        stateDataStore.data.map { it[LAST_SELECTED_VIEW_TYPE] ?: DEFAULT_VIEW_TYPE }

    override suspend fun updateNotifyRequestData() {
        settingsDataStore.edit { state -> state[NOTIFY_FIRST_REQUEST] = true }
    }

    override suspend fun getNotifyRequestData(): Flow<Boolean> =
        settingsDataStore.data.map { it[NOTIFY_FIRST_REQUEST] ?: false }

    override suspend fun getSelectedDay(): Flow<LocalDate?> =
        stateDataStore.data.map { state ->
            val storedDay = state[CALENDAR_SELECTED_DAY]
            val selectedDay = if (storedDay != null) {
                localDateFromString(storedDay)
            } else null
            return@map selectedDay
        }
}
