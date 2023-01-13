package com.If5a.booksdictionary.databases;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_BOOKS = "Books";

    static final class EnglishIndonesiaColumn implements BaseColumns {
        static String BOOK_ISBN = "isbn";
        static String BOOK_TITLE = "title";
        static String BOOK_AUTHOR = "author";
        static String BOOK_YEAR = "year";
        static String BOOK_PUBLISHER = "publisher";
        static String BOOK_IMAGE_S = "image_S";
        static String BOOK_IMAGE_M = "image_M";
        static String BOOK_IMAGE_L = "image_L";
    }
}
