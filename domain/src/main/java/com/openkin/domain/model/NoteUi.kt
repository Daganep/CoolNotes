package com.openkin.domain.model

import com.openkin.domain.utils.DEFAULT_NOTE_COLOR
import com.openkin.domain.utils.EMPTY_STRING
import java.time.LocalDate

data class NoteUi(
    val id: Int,
    val title: String,
    val text: String,
    val createDateMS: Long,
) {
    var archived: Boolean = false
    var editDateMS: Long = 0L
    var color: String = DEFAULT_NOTE_COLOR
    var targetDate: LocalDate = LocalDate.now()
    var notifyTime: String = EMPTY_STRING

    override fun hashCode(): Int =
        title.hashCode() +
            text.hashCode() +
            createDateMS.hashCode() * 31

    override fun equals(other: Any?): Boolean =
        other is NoteUi
            && this.id == other.id
            && this.title == other.title
            && this.text == other.text
            && this.createDateMS == other.createDateMS
            && this.archived == other.archived
            && this.editDateMS == other.editDateMS
            && this.color == other.color
            && this.targetDate == other.targetDate
            && this.notifyTime == other.notifyTime
}
