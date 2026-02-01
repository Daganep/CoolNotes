package com.openkin.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_notes_database")
data class NoteDbo(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("createDateMS") val createDateMS: Long,
    @ColumnInfo("editDateMS") val editDateMS: Long,
    @ColumnInfo("archived") val archived: Boolean,
)
