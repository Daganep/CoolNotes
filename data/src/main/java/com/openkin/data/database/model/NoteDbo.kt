package com.openkin.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_notes_database")
data class NoteDbo(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo("title") val timestamp: Long,
    @ColumnInfo("description") val requestText: String,
    @ColumnInfo("createDate") val sourceCode: String,
    @ColumnInfo("archived") val archived: Boolean,
)
