package com.imceits.aungtuntun.toolsbox.data.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import com.imceits.aungtuntun.toolsbox.data.Status
import java.util.*

@DatabaseView("SELECT tr.*, tl.tool_name, tl.image FROM Tools_rent tr JOIN Tools tl ON tr.tool_id = tl.tool_id")
data class ToolsRentDto (
    @ColumnInfo(name = "tool_rent_id")
    val id: Int,
    @ColumnInfo(name = "tool_id")
    val toolId: Int,
    @ColumnInfo(name = "friend_id")
    val friendId: Int,
    val status: Status,
    @ColumnInfo(name = "created_date")
    val createdDate: Date,
    val count: Int,
    @ColumnInfo(name = "tool_name")
    val name: String,
    @ColumnInfo(name = "image")
    val  image: String,
    @ColumnInfo(name = "returned_count")
    val returnedCount: Int
) {
}