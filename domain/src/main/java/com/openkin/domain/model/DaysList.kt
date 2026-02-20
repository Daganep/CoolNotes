package com.openkin.domain.model

import java.time.LocalDate
import java.time.temporal.WeekFields
import com.openkin.domain.utils.DAYS_IN_WEEK

class DaysList(selectedDate: LocalDate = LocalDate.now()) {

    private val now = LocalDate.now()
    private val firstDay = selectedDate.minusDays(now.dayOfMonth.toLong()-1)

    private val selectedDateWeek = firstDay.get(WeekFields.ISO.weekOfMonth())
    //Иногда LocalDate для первой недели взвращает 0, иногда 1, для стабильности 0 приравниваю к 1
    private val weekOfMonth = if (selectedDateWeek == 0) 1 else selectedDateWeek
    private val dayOfWeek = firstDay.dayOfWeek.value

    private val listOfDays = mutableListOf<LocalDate>()

    fun getDaysList(): List<LocalDate> {
        if (listOfDays.isEmpty()) {
            val curDayPosition = (weekOfMonth-1) * DAYS_IN_WEEK + (dayOfWeek)

            for (i in 1..DAYS_ON_LIST) {
                if (i < curDayPosition) {
                    val currentDayPosition = curDayPosition - (curDayPosition - i)
                    val currentDay = firstDay.minusDays((curDayPosition - currentDayPosition).toLong())
                    listOfDays.add(currentDay)
                } else if (i > curDayPosition) {
                    val currentDayPosition = i - curDayPosition
                    val currentDay = firstDay.plusDays(currentDayPosition.toLong())
                    listOfDays.add(currentDay)
                } else {
                    listOfDays.add(firstDay)
                }
            }
        }
        return listOfDays.toList()
    }

    fun getToday(): LocalDate = now

    companion object {
        private const val DAYS_ON_LIST = 42
    }
}
