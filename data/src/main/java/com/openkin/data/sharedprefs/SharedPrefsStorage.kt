package com.openkin.data.sharedprefs

import android.content.SharedPreferences
import androidx.core.content.edit
import com.openkin.domain.utils.DEFAULT_VIEW_TYPE
import com.openkin.domain.utils.SHARED_PREFS_VIEW_TYPE

class SharedPrefsStorage(
    private val sharedPrefs: SharedPreferences
) : ISharedPrefsStorage {

    override fun  saveViewType(viewType: Int) {
        sharedPrefs.edit { putInt(SHARED_PREFS_VIEW_TYPE, viewType) }
    }

    override fun  getViewType() : Int =
        sharedPrefs.getInt(SHARED_PREFS_VIEW_TYPE, DEFAULT_VIEW_TYPE)
}
