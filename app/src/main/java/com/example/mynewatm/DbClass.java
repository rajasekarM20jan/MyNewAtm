package com.example.mynewatm;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import controller.dbControllerMainActivity;

public class DbClass extends SQLiteOpenHelper { //Database Creation class

    DbClass dbClass;
    String name,userName,MPin,balance,login;
    SQLiteDatabase dbReader, dbWriter;
    Cursor c;
    String result;
    ContentValues values;

    public DbClass(@Nullable Context context) {
        super(context, "UserAccounts", null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("CREATE TABLE UserDetails(name text,userName text,MPin text,balance text,login text)");
        myDB.execSQL("CREATE TABLE Transactions(userName text,transactions text,credit text)");
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("DROP TABLE IF EXISTS Interns");
        onCreate(myDB);
    }

    public void getDataForLogin(String username, String pin){
        dbReader=this.getReadableDatabase();
        try{
            c=dbReader.rawQuery("SELECT * FROM UserDetails WHERE userName=?",new String[]{username});
            c.moveToNext();
            if(c.getString(1).equals(username)){
                if (c.getString(2).equals(pin)){
                    name=c.getString(0);
                    userName=c.getString(1);
                    MPin=c.getString(2);
                    balance=c.getString(3);
                    login=c.getString(4);
                    result="pass";
                }
            }
        }catch(Exception e){
            result="fail";
        }

    }
    public void updateLoginStatus(String userName,String login){
        dbReader=this.getReadableDatabase();
        System.out.println("MyUserName"+userName);
        c=dbReader.rawQuery("SELECT * FROM UserDetails WHERE userName=?",new String[]{userName});
        c.moveToNext();
        name=c.getString(0);
        this.userName=c.getString(1);
        MPin=c.getString(2);
        balance=c.getString(3);
        this.login=login;
        dbWriter=this.getWritableDatabase();
        values=new ContentValues();
        values.put("name",name);
        values.put("userName",this.userName);
        values.put("MPin",MPin);
        values.put("balance",balance);
        values.put("login",this.login);
        dbWriter.update("UserDetails",values,"userName=?",new String[]{userName});
    }

    public void updateData(String name,String userName,String MPin,String balance,String login){
        dbReader=this.getReadableDatabase();
        System.out.println("MyUserName   :@#"+userName);
        c=dbReader.rawQuery("SELECT * FROM UserDetails WHERE userName=?",new String[]{userName});
        c.moveToNext();
        this.name=c.getString(0);
        this.userName=c.getString(1);
        this.MPin=c.getString(2);
        this.balance=c.getString(3);
        this.login=c.getString(4);
        dbWriter=this.getWritableDatabase();
        System.out.println("MyUserName Success");
        values=new ContentValues();
        values.put("name",name);
        values.put("userName",userName);
        values.put("MPin",MPin);
        values.put("balance",balance);
        values.put("login",login);
        dbWriter.update("UserDetails",values,"userName=?",new String[]{userName});
        System.out.println("MyUserName Success");
    }
    public void getData(String userName){
        dbReader=this.getReadableDatabase();
        System.out.println("MyUserName  :   1 "+userName);
        c=dbReader.rawQuery("SELECT * FROM UserDetails WHERE userName=?",new String[]{userName});
        c.moveToNext();
        name=c.getString(0);
        this.userName=c.getString(1);
        MPin=c.getString(2);
        balance=c.getString(3);
        login=c.getString(4);
    }
    public void updateTransactions(String userName,String transaction,String credit){
        dbWriter=this.getWritableDatabase();
        values=new ContentValues();
        values.put("userName",userName);
        values.put("transactions",transaction);
        values.put("credit",credit);
        dbWriter.insert("Transactions",null,values);
    }
}
