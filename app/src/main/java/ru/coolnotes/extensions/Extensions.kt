package ru.coolnotes.extensions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.openkin.domain.utils.COOL_NOTES_STATE_PREFS

val Context.StateDataStore: DataStore<Preferences> by preferencesDataStore(COOL_NOTES_STATE_PREFS)
