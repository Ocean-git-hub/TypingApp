package com.example.typingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

class OperateDatabase {
    private SQLiteDatabase database;

    OperateDatabase(Context context) {
        DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(context);
        database = databaseOpenHelper.getWritableDatabase();
    }

    SQLiteDatabase getDatabase() {
        return database;
    }

    long addSentence(String sentence, String howReading, String genre) {
        ContentValues contentValues = new ContentValues();
        if (!genre.isEmpty()) {
            contentValues.put("genre", genre);
        }
        contentValues.put("sentence", sentence);
        contentValues.put("howReading", howReading);
        contentValues.put("numberOfSentence", sentence.length());
        return database.insert("sentence", "0", contentValues);
    }

    int deleteSentence(String sentence) {
        return database.delete("sentence", "sentence=?", new String[]{sentence});
    }

    void closeDatabase() {
        database.close();
    }

    int updateSentence(String updateSentence, String whereSentence) {
        ContentValues values = new ContentValues();
        values.put("sentence", updateSentence);
        return database.update("sentence", values, "sentence=?", new String[]{whereSentence});
    }

    int updateHowReading(String updateHowReading, String whereSentence) {
        ContentValues values = new ContentValues();
        values.put("howReading", updateHowReading);
        return database.update("sentence", values, "sentence=?", new String[]{whereSentence});
    }

    int updateGenre(String genre, String whereSentence) {
        ContentValues values = new ContentValues();
        values.put("genre", genre);
        return database.update("sentence", values, "sentence=?", new String[]{whereSentence});
    }

    int deleteAllSentence() {
        return database.delete("sentence", "1", null);
    }

    Cursor showDatabase() {
        return database.query("sentence", new String[]{"sentence", "howReading"}, null, null, null, null, null);
    }

    Cursor getSentence() {
        return database.query("sentence", null, null, null, null, null, null);
    }

    Cursor getSentence(int characterNumber) {
        return database.query("sentence", null, "numberOfSentence<=?", new String[]{String.valueOf(characterNumber)}, null, null, null);
    }

    Cursor getSentence(String genre) {
        return database.query("sentence", null, "genre=?", new String[]{genre}, null, null, null);
    }

    Cursor getSentence(String genre, int characterNumber) {
        return database.query("sentence", null, "genre=? and numberOfSentence<=?", new String[]{genre, String.valueOf(characterNumber)}, null, null, null);
    }

    String getGenre(String sentence) {
        String genre = "";
        Cursor cursor = database.query("sentence", null, "sentence=?", new String[]{sentence}, null, null, null);
        boolean mov = cursor.moveToFirst();
        while (mov) {
            genre = cursor.getString(3);
            mov = cursor.moveToNext();
        }
        cursor.close();
        return genre;
    }

    boolean searchSentence(String sentence) {
        if (database.query("sentence", null, "sentence=?",
                new String[]{sentence}, null, null, null).getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
