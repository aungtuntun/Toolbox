package com.imceits.aungtuntun.toolsbox.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.imceits.aungtuntun.toolsbox.data.Status
import java.util.*

@Entity(tableName = "Tools_rent", indices = [Index("tool_id"), Index("friend_id")])
data class ToolsRent(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tool_rent_id")
    val id: Int,
    @ColumnInfo(name = "tool_id")
    val toolId: Int,
    @ColumnInfo(name = "friend_id")
    val friendId: Int,
    @ColumnInfo(name = "status")
    val status: Status,
    @ColumnInfo(name = "created_date")
    val createdDate: Date,
    @ColumnInfo(name = "returned_count")
    val returnedCount: Int,
    @ColumnInfo(name = "count")
    val count: Int
) {
}