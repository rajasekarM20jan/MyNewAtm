package com.example.mynewatm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class dbController {

    SQLiteDatabase dbReader,dbWriter;
    ContentValues values;
    String[] data={"name","userName","MPin","balance","login"};
    public dbController(){

    }
}
