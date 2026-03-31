package com.openkin.domain.interactor

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ISettingsInteractor {

    suspend fun saveViewType(viewType: Int)
    suspend fun getStoredViewType(): Flow<Int>
    suspend fun saveSelectedTheme(themeName: String)
    suspend fun getStoredTheme(): Flow<String>
    suspend fun getNotifyRequestStatus(): Flow<Boolean>
    suspend fun updateNotifyRequestStatus()
    suspend fun getSelectedDay(): Flow<LocalDate?>
}
