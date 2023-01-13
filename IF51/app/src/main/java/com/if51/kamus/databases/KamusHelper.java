package com.if51.kamus.databases;

import static android.provider.BaseColumns._ID;
import static com.if51.kamus.databases.DatabaseContact.EnglishIndonesiaColumn.ENGLISH_INDONESIA_DESCRIPTION;
import static com.if51.kamus.databases.DatabaseContact.EnglishIndonesiaColumn.ENGLISH_INDONESIA_TITLE;
import static com.if51.kamus.databases.DatabaseContact.TABLE_ENGLISH_INDONESIA_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.if51.kamus.models.Kamus;

import java.util.ArrayList;

public class KamusHelper {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database =databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<Kamus> getAllDataEnglishIndonesiaByTitle(String title) {
        Cursor cursor = database.query(TABLE_ENGLISH_INDONESIA_NAME, null, ENGLISH_INDONESIA_TITLE + " LIKE ?"
                , new String[]{"%"+ title + "%"},null,null,_ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<Kamus> arrayList = new ArrayList<>();
        Kamus kamus;
        if (cursor.getCount() > 0) {
            do{
                kamus = new Kamus();
                kamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamus.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ENGLISH_INDONESIA_TITLE)));
                kamus.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(ENGLISH_INDONESIA_DESCRIPTION)));
                arrayList.add(kamus);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public ArrayList<Kamus> getAllDataEnglishIndonesia() {
        Cursor cursor = database.query(TABLE_ENGLISH_INDONESIA_NAME, null, null, null,null,null,_ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<Kamus> arrayList = new ArrayList<>();
        Kamus kamus;
        if (cursor.getCount() > 0) {
            do{
                kamus = new Kamus();
                kamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamus.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ENGLISH_INDONESIA_TITLE)));
                kamus.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(ENGLISH_INDONESIA_DESCRIPTION)));
                arrayList.add(kamus);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertDataEnglishIndonesia(Kamus kamus) {
        ContentValues cv = new ContentValues();
        cv.put(ENGLISH_INDONESIA_TITLE, kamus.getTitle());
        cv.put(ENGLISH_INDONESIA_DESCRIPTION, kamus.getDescription());
        return database.insert(TABLE_ENGLISH_INDONESIA_NAME, null, cv);
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransactionDataEnglishIndonesia(Kamus kamus){
        String sql = "INSERT INTO " + TABLE_ENGLISH_INDONESIA_NAME + " (" + ENGLISH_INDONESIA_TITLE + ", " + ENGLISH_INDONESIA_DESCRIPTION + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamus.getTitle());
        stmt.bindString(2, kamus.getDescription());
        stmt.execute();
        stmt.clearBindings();
    }

    public int updateDataEnglishIndonesia(Kamus kamus) {
        ContentValues cv = new ContentValues();
        cv.put(ENGLISH_INDONESIA_TITLE, kamus.getTitle());
        cv.put(ENGLISH_INDONESIA_DESCRIPTION, kamus.getDescription());
        return database.update(TABLE_ENGLISH_INDONESIA_NAME, cv,
                _ID + "= '" + kamus.getId() + "'",null);
    }
    public int deleteDataEnglishIndonesia(int id) {
        return database.delete(TABLE_ENGLISH_INDONESIA_NAME,
                _ID + "= '" + id + "'",null);
    }
}
