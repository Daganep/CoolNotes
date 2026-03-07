package com.openkin.domain.repository

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ISettingsRepository {

    suspend fun saveViewType(viewType: Int)
    suspend fun getStoredViewType(): Flow<Int>
    suspend fun updateNotifyRequestData()
    suspend fun getNotifyRequestData(): Flow<Boolean>
    suspend fun getSelectedDay(): Flow<LocalDate?>
}
