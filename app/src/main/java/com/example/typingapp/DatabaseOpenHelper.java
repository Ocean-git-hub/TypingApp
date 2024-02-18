package com.example.typingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseOpenHelper extends SQLiteOpenHelper {
    DatabaseOpenHelper(Context context) {
        super(context, "SentenceDB", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table sentence("
                + "sentence text not null,"
                + "howReading text not null,"
                + "numberOfSentence int not null,"
                + "genre text"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            db.execSQL("ALTER TABLE sentence add genre text");
        }
    }
}
