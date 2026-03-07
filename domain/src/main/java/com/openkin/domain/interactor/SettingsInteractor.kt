package com.openkin.domain.interactor

import com.openkin.domain.repository.ISettingsRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class SettingsInteractor(
    private val settingsRepository: ISettingsRepository,
) : ISettingsInteractor {

    override suspend fun updateNotifyRequestStatus() {
        settingsRepository.updateNotifyRequestData()
    }

    override suspend fun getNotifyRequestStatus(): Flow<Boolean> =
        settingsRepository.getNotifyRequestData()

    override suspend fun saveViewType(viewType: Int) {
        settingsRepository.saveViewType(viewType)
    }

    override suspend fun getStoredViewType(): Flow<Int> =
        settingsRepository.getStoredViewType()

    override suspend fun getSelectedDay(): Flow<LocalDate?> =
        settingsRepository.getSelectedDay()
}
