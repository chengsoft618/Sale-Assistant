package com.shoniz.saledistributemobility.data.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static String toDate(Long timestamp) {
        return timestamp == null || timestamp == 0 ? "" : new Date(timestamp).toString();
    }
}