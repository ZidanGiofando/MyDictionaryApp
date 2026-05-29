package com.example.myapplication11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DictionaryDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dictionary.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "dictionary";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ENGLISH = "english";
    public static final String COLUMN_INDONESIA = "indonesia";

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ENGLISH + " TEXT NOT NULL UNIQUE, " +
                    COLUMN_INDONESIA + " TEXT NOT NULL" +
                    ");";

    public DictionaryDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void insert(SQLiteDatabase db, String english, String indonesia) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ENGLISH, english);
        cv.put(COLUMN_INDONESIA, indonesia);
        db.insert(TABLE_NAME, null, cv);
    }

    public long addEntry(String english, String indonesia) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ENGLISH, english);
        cv.put(COLUMN_INDONESIA, indonesia);
        return db.insert(TABLE_NAME, null, cv);
    }

    public String queryTranslation(String english) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_INDONESIA};
        String selection = COLUMN_ENGLISH + " = ?";
        String[] selectionArgs = {english};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        String result = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INDONESIA));
            }
            cursor.close();
        }
        return result;
    }

    public List<Word> listAll() {
        List<Word> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_ENGLISH, COLUMN_INDONESIA};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, COLUMN_ENGLISH + " ASC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String english = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENGLISH));
                String indonesia = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INDONESIA));
                result.add(new Word(id, english, indonesia));
            }
            cursor.close();
        }
        return result;
    }

    public int deleteById(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int updateWord(int id, String english, String indonesia) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ENGLISH, english);
        values.put(COLUMN_INDONESIA, indonesia);
        return db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}
        );
    }

}
