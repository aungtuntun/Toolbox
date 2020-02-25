package com.imceits.aungtuntun.toolsbox.data

import androidx.room.TypeConverter

class EnumConverter {

    @TypeConverter
    public fun fromStatusToString(status: Status?) : String? {
        return status?.value
    }

    @TypeConverter
    public fun getStatus(status: String?) : Status {
        if (!status.isNullOrEmpty()) {
            return if (Status.BORROWED.value == status) Status.BORROWED else Status.RETURNED
        }else
            return Status.RETURNED
    }
}