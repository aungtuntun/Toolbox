package com.imceits.aungtuntun.toolsbox.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Friends")
data class Friends(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "friend_id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String
)