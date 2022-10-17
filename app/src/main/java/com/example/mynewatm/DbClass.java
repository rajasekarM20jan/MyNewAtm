package com.example.mynewatm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

import androidx.annotation.Nullable;

import controller.dbControllerMainActivity;

public class DbClass extends SQLiteOpenHelper { //Database Creation class

    DbClass dbClass;
    SQLiteDatabase dbReader, dbWriter;
    Cursor c;
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
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
       /* myDB.execSQL("DROP TABLE IF EXISTS Interns");
        onCreate(myDB);*/
    }

    public void getDataForLogin(String username, String pin){
        dbReader=this.getReadableDatabase();
        c=dbReader.rawQuery("SELECT * FROM UserDetails",null);
        boolean b=false;
        while(c.moveToNext()){
            System.out.println("MyT  "+username+"OOPs    "+pin);
            if(c.getString(1).equals(username)){
                String name,userName,MPin,balance,login,position;
                b=true;
                name=c.getString(0);
                userName=c.getString(1);
                MPin=c.getString(2);
                balance=c.getString(3);
                login=c.getString(4);
                position=String.valueOf(c.getPosition());
                System.out.println("MyT"+name+userName+MPin+balance+login+position);
                dbControllerMainActivity db= new dbControllerMainActivity();
                db.verification(username,pin,name,userName,MPin,balance,login,position);
                c.close();
            }
        }
        if(!b){
            MainActivity m=new MainActivity();
            m.getError();
        }
    }
    //Error Area
    //Gives ERROR as  java.lang.NullPointerException: Attempt to invoke virtual method ...
    //.. 'java.io.File android.content.Context.getDatabasePath(java.lang.String)' on a null object reference.
    public void updateData(String name,String userName,String MPin,String balance,String login){
        System.out.println("MYTransaction"+name+userName+MPin+balance+login);
        dbWriter=this.getWritableDatabase();
        // _____________Above line gives error
        values = new ContentValues();
        values.put("name",name);
        values.put("userName",userName);
        values.put("MPin",MPin);
        values.put("balance",balance);
        values.put("login",login);
        System.out.println("MyTr"+values.get(name));
        dbWriter.update("UserDetails",values,"userName=?",new String[]{userName});
    }
}
