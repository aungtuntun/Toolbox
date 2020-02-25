package com.imceits.aungtuntun.alephcodeassignment.data;

import android.text.TextUtils;

import androidx.room.TypeConverter;

class EnumConverter {

    @TypeConverter
 public static String fromStatusToString(Status status) {
     return status == null ? null : status.getText();
 }

 @TypeConverter
 public static Status getStatus(String status) {
        if (!TextUtils.isEmpty(status)) {
            return Status.BORROWED.getText().equals(status) ? Status.BORROWED : Status.RETURNED;
        }else
            return Status.RETURNED;
 }
}
