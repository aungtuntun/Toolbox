package com.imceits.aungtuntun.toolsbox.data

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    public fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    public fun toTimestamp(date: Date?): Long? {
        return date?.time
    }

}