package com.openkin.data.datastorekeys

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

val CALENDAR_SELECTED_DAY = stringPreferencesKey("CALENDAR_SELECTED_DAY")
val LAST_SELECTED_VIEW_TYPE = intPreferencesKey("LAST_SELECTED_VIEW_TYPE")
val NOTIFY_FIRST_REQUEST = booleanPreferencesKey("NOTIFY_FIRST_REQUEST")
