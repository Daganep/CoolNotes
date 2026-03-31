package com.openkin.presentation.ui.settings.model

import androidx.annotation.StringRes
import com.openkin.presentation.R

enum class AppTheme(@StringRes val viewNameId: Int) {
    SYSTEM(R.string.settings_theme_system),
    LIGHT(R.string.settings_theme_light),
    DARK(R.string.settings_theme_dark),
}
