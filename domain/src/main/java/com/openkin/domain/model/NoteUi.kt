package com.openkin.domain.model

import com.openkin.domain.utils.DEFAULT_NOTE_COLOR

data class NoteUi(
    val id: Int,
    val title: String,
    val description: String,
    val createDateMS: Long,
) {
    var archived: Boolean = false
    var editDateMS: Long = 0L
    var color: String = DEFAULT_NOTE_COLOR

    override fun hashCode(): Int =
        title.hashCode() +
            description.hashCode() +
            createDateMS.hashCode() * 31

    override fun equals(other: Any?): Boolean =
        other is NoteUi
            && this.id == other.id
            && this.title == other.title
            && this.description == other.description
            && this.createDateMS == other.createDateMS
            && this.archived == other.archived
            && this.editDateMS == other.editDateMS
            && this.color == other.color
}
