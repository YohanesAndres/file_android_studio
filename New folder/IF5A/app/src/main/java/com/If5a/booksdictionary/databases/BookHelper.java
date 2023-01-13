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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.If5a.booksdictionary.models.Book;

import java.util.ArrayList;

public class BookHelper {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public BookHelper(Context context) {
        this.context = context;
    }

    public BookHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database =databaseHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        databaseHelper.close();
    }
    public ArrayList<Book> getAllDataBook(){
        Cursor cursor = database.query(TABLE_BOOKS, null, null,
                null,null, null, _ID + " ASC", "100");
        cursor.moveToFirst();
        ArrayList<Book> arrayList = new ArrayList<>();
        Book book;
        if(cursor.getCount() > 0){
            do {
                book = new Book();
                book.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                book.setISBN(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_ISBN)));
                book.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_TITLE)));
                book.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_AUTHOR)));
                book.setYear(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_YEAR)));
                book.setPublisher(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_PUBLISHER)));
                book.setS(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_IMAGE_S)));
                book.setM(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_IMAGE_M)));
                book.setL(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_IMAGE_L)));

                arrayList.add(book);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Book> getAllDataBookByTitle(String title){
        Cursor cursor = database.query(TABLE_BOOKS, null, BOOK_TITLE + " LIKE ?",
                new String[]{"%"+ title + "%"},null, null, _ID + " ASC", "100");
        cursor.moveToFirst();
        ArrayList<Book> arrayList = new ArrayList<>();
        Book book;
        if(cursor.getCount() > 0){
            do {
                book = new Book();
                book.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                book.setISBN(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_ISBN)));
                book.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_TITLE)));
                book.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_AUTHOR)));
                book.setYear(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_YEAR)));
                book.setPublisher(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_PUBLISHER)));
                book.setS(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_IMAGE_S)));
                book.setM(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_IMAGE_M)));
                book.setL(cursor.getString(cursor.getColumnIndexOrThrow(BOOK_IMAGE_L)));


                arrayList.add(book);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertDataBook(Book book) {
        ContentValues cv = new ContentValues();
        cv.put(BOOK_ISBN, book.getISBN());
        cv.put(BOOK_TITLE, book.getTitle());
        cv.put(BOOK_AUTHOR, book.getAuthor());
        cv.put(BOOK_YEAR, book.getYear());
        cv.put(BOOK_PUBLISHER, book.getPublisher());
        cv.put(BOOK_IMAGE_S, book.getS());
        cv.put(BOOK_IMAGE_M, book.getM());
        cv.put(BOOK_IMAGE_L, book.getL());
        return database.insert(TABLE_BOOKS, null, cv);
    }

    public long updateDataBook(Book book) {
        ContentValues cv = new ContentValues();
        cv.put(BOOK_ISBN, book.getISBN());
        cv.put(BOOK_TITLE, book.getTitle());
        cv.put(BOOK_AUTHOR, book.getAuthor());
        cv.put(BOOK_YEAR, book.getYear());
        cv.put(BOOK_PUBLISHER, book.getPublisher());
        cv.put(BOOK_IMAGE_S, book.getS());
        cv.put(BOOK_IMAGE_M, book.getM());
        cv.put(BOOK_IMAGE_L, book.getL());
        return database.update(TABLE_BOOKS, cv, _ID + " ='" + book.getId() + "'", null);
    }

    public long deleteDataBook(int id) {
        return database.delete(TABLE_BOOKS, _ID + " ='" + id + "'", null);
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransactionDataBook(Book book){
        String sql = "INSERT INTO " + TABLE_BOOKS + " (" +
                BOOK_TITLE + ", " +
                BOOK_AUTHOR + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, book.getTitle());
        stmt.bindString(2, book.getAuthor());
        stmt.execute();
        stmt.clearBindings();
    }
}
