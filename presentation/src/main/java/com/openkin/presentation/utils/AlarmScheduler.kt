package com.openkin.presentation.utils

import com.openkin.domain.model.NotificationModel

interface AlarmScheduler {

    fun scheduleNotification(notification: NotificationModel)

    fun cancelNotification(notification: NotificationModel)
}
