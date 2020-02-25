package com.imceits.aungtuntun.toolsbox.data.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView("SELECT DISTINCT tl.tool_id, tl.tool_name, tl.image, tl.item_count, IFNULL(SUM(tr.count), 0) AS rent_count," +
        " IFNULL(SUM(tr.returned_count), 0) AS return_count FROM Tools tl LEFT JOIN Tools_rent tr ON tl.tool_id = tr.tool_id " +
        " GROUP BY tl.tool_id, tl.tool_name, tl.image, tl.item_count")
data class ToolsDto (
    @ColumnInfo(name = "tool_id")
    val id: Int,
    @ColumnInfo(name = "tool_name")
    val name: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "item_count")
    val count: Int,
    @ColumnInfo(name = "rent_count")
    val rentCount: Int
) {
}