package com.if51.kamus.databases;

import static android.provider.BaseColumns._ID;
import static com.if51.kamus.databases.DatabaseContact.EnglishIndonesiaColumn.ENGLISH_INDONESIA_DESCRIPTION;
import static com.if51.kamus.databases.DatabaseContact.EnglishIndonesiaColumn.ENGLISH_INDONESIA_TITLE;
import static com.if51.kamus.databases.DatabaseContact.TABLE_ENGLISH_INDONESIA_NAME;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbkamus";
    private static final int DATABASE_VERSION = 1;
    public static String CREATE_TABLE_ENGLISH_INDONESIA = "CREATE TABLE "
            + TABLE_ENGLISH_INDONESIA_NAME + " ("+ _ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ENGLISH_INDONESIA_TITLE + " TEXT NOT NULL, " + ENGLISH_INDONESIA_DESCRIPTION + " TEXT NOT NULL);";

    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENGLISH_INDONESIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENGLISH_INDONESIA_NAME);
        onCreate(db);
    }
}
