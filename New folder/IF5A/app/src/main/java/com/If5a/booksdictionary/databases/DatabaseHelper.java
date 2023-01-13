package com.If5a.booksdictionary.databases;

import static android.provider.UserDictionary.Words._ID;
import static com.If5a.booksdictionary.databases.DatabaseContract.EnglishIndonesiaColumn.BOOK_AUTHOR;
import static com.If5a.booksdictionary.databases.DatabaseContract.EnglishIndonesiaColumn.BOOK_IMAGE_L;
import static com.If5a.booksdictionary.databases.DatabaseContract.EnglishIndonesiaColumn.BOOK_IMAGE_M;
import static com.If5a.booksdictionary.databases.DatabaseContract.EnglishIndonesiaColumn.BOOK_IMAGE_S;
import static com.If5a.booksdictionary.databases.DatabaseContract.EnglishIndonesiaColumn.BOOK_ISBN;
import static com.If5a.booksdictionary.databases.DatabaseContract.EnglishIndonesiaColumn.BOOK_PUBLISHER;
import static com.If5a.booksdictionary.databases.DatabaseContract.EnglishIndonesiaColumn.BOOK_TITLE;
import static com.If5a.booksdictionary.databases.DatabaseContract.EnglishIndonesiaColumn.BOOK_YEAR;
import static com.If5a.booksdictionary.databases.DatabaseContract.TABLE_BOOKS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "db_books";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_BOOK =
            "CREATE TABLE " + TABLE_BOOKS + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BOOK_ISBN + " VARCHAR(32) NOT NULL, " +
                    BOOK_TITLE + " VARCHAR(255) NOT NULL, " +
                    BOOK_AUTHOR + " VARCHAR(255) NOT NULL, " +
                    BOOK_YEAR + " INTEGER NOT NULL, " +
                    BOOK_PUBLISHER + " VARCHAR(255) NOT NULL, " +
                    BOOK_IMAGE_S + " VARCHAR(255) NOT NULL, " +
                    BOOK_IMAGE_M + " VARCHAR(255) NOT NULL, " +
                    BOOK_IMAGE_L + " VARCHAR(255) NOT NULL);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(sqLiteDatabase);
    }
}