package com.example.mynewatm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbClass extends SQLiteOpenHelper {
    public DbClass(@Nullable Context context) {
        super(context, "internData", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("CREATE TABLE Interns(name text,userName text,MPin number,balance text,login text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
       /* myDB.execSQL("DROP TABLE IF EXISTS Interns");
        onCreate(myDB);*/
    }


}
