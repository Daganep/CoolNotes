package com.openkin.presentation.utils

import com.openkin.presentation.ui.addnote.model.NotificationModel

interface AlarmScheduler {

    fun scheduleNotification(notification: NotificationModel)

    fun cancelNotification(notification: NotificationModel)
}
