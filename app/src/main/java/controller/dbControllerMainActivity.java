package controller;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mynewatm.DbClass;
import com.example.mynewatm.MainActivity;
import com.example.mynewatm.R;

import java.util.ArrayList;

import model.UserDetailsDB;

public class dbControllerMainActivity {
    //variable declaration
    DbClass dbClass;
    SQLiteDatabase dbReader,dbWriter;
    ContentValues values;
    String[] data={"name","userName","MPin","balance","login"};
    MainActivity UserVerification;
    EditText etForUserName,etForMPin;
    Button mainPageButton;
    TextView linkForSignup;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    String position,name,userName,MPin,balance,login;
    public dbControllerMainActivity(){

    }
    //creating parameterized constructor for initializing data
    public dbControllerMainActivity(MainActivity mainActivity){
        this.UserVerification =mainActivity;
        etForUserName= UserVerification.findViewById(R.id.etForUserName);
        etForMPin= UserVerification.findViewById(R.id.etForMPin);
        mainPageButton= UserVerification.findViewById(R.id.mainPageButton);
        linkForSignup= UserVerification.findViewById(R.id.linkForSignup);
        sp= UserVerification.getSharedPreferences("MyPref",MODE_PRIVATE);
        edit= sp.edit();
        linkForSignup.setClickable(true);
    }

    //creating method for verification in login
    public void loginActivity(MainActivity mainActivity){
        this.UserVerification =mainActivity;
        dbClass=new DbClass(UserVerification);
        dbReader=dbClass.getReadableDatabase();
        Cursor c= dbReader.query("UserDetails",data,null,null,null,null,null);
        String userName=String.valueOf(UserVerification.etForUserName.getText());
        String mPin=String.valueOf(UserVerification.etForMPin.getText());
        sp= UserVerification.getSharedPreferences("MyPref",MODE_PRIVATE);
        boolean b=false;
        while(c.moveToNext()){ //checks with the database if user name and pin are existing
            if (c.getString(1).equals(userName)) {
                if(c.getString(2).equals(mPin)){
                    edit= sp.edit();
                    edit.putString("userName",userName);
                    edit.commit();
                    b=true;
                    this.name=c.getString(0);
                    this.userName=c.getString(1);
                    this.MPin=c.getString(2);
                    this.balance=c.getString(3);
                    this.login=c.getString(4);
                    position=String.valueOf(c.getPosition());
                    String uN=sp.getString("userName","no");
                    Cursor c2=dbReader.rawQuery("SELECT * FROM UserDetails where userName=?",new String[]{uN});
                    c2.moveToNext();
                    if(c2.getString(4).equals("false")) {
                        dbWriter=dbClass.getWritableDatabase();
                        values= new ContentValues();
                        values.put("userName",userName);
                        values.put("name",name);
                        values.put("login","true");
                        values.put("balance",balance);
                        values.put("MPin",MPin);
                        dbWriter.update("UserDetails",values,"userName=?",new String[]{userName});
                        UserVerification.getDashboard(position);
                        break;
                    }else{
                        UserVerification.alreadyLoggedIn(name,this.userName,MPin,balance,login);
                    }
                }
            }
        }
        if(!b){ //if user is not existing it is passed to get Error method of main activity.
           UserVerification.getError();
        }
    }




    /*
    Gives ERROR as  java.lang.NullPointerException: Attempt to invoke virtual method 'java.io.File android.content.Context.getDatabasePath(java.lang.String)' on a null object reference.
    public void verification(String etName,String etPin,String cName,String cUserName,String cMPin,String cBalance,String cLogin,String position){
        MainActivity m=new MainActivity();
        if(etName.equals(cUserName)){
            if(etPin.equals(cMPin)){
                if(cLogin.equals("false")){
                    m.sendToDbClass(cName,cUserName,cMPin,cBalance,"true");
                    m.getDashboard(position);
                }else{
                    m.alreadyLoggedIn(cName,cUserName,cMPin,cBalance,cLogin);
                }
            }
        }else{
            m.getError();
        }
    }*/
   /* public void getFromDb(String cname,String cUserName,String cMPin,String cBalance,String cLogin){
        System.out.println("MyTransfer :"+cname+cUserName+cMPin+cBalance+cLogin);
        *//*if(cLogin.equals("true")){
            MainActivity m=new MainActivity();
        }*//*
    }*/
}
