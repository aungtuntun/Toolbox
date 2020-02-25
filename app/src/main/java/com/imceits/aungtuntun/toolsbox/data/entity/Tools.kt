package com.imceits.aungtuntun.toolsbox.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tools")
data class Tools(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tool_id")
    val id: Int,
    @ColumnInfo(name = "tool_name")
    val name:String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "item_count")
    val count: Int
) {

}