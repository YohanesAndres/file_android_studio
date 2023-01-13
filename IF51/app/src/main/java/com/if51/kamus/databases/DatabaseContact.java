package com.if51.kamus.databases;

import android.provider.BaseColumns;

public class DatabaseContact {
    static String TABLE_ENGLISH_INDONESIA_NAME = "english_indonesia";

    static final class EnglishIndonesiaColumn implements BaseColumns {
        static String ENGLISH_INDONESIA_TITLE = "title";
        static String ENGLISH_INDONESIA_DESCRIPTION = "description";
    }
}
