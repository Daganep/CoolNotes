package com.openkin.domain.model

data class NoteUi(
    val title: String,
    val description: String,
    val createDateMS: Long,
) {
    val id: Int
    var archived: Boolean = false
    var editDateMS: Long = 0L

    init { id = this.hashCode() }

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
}
