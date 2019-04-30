package com.shoniz.saledistributemobility.utility.data.sqlite;

import android.content.Context;

import com.shoniz.saledistributemobility.utility.Enums;

public class DatabaseManager {
    public static void deleteDatabase(Context context, Enums.DBName dbName) {
        context.deleteDatabase(dbName + ".db");
    }
}
