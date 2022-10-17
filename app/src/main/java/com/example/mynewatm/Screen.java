package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        SharedPreferences sp=getSharedPreferences("MyPref",MODE_PRIVATE);
        String a=sp.getString("userName","no");
        DbClass dbClass=new DbClass(this);
        dbClass.getData(a);
        String userName=dbClass.userName;
        String name=dbClass.name;
        String MPin=dbClass.MPin;
        String balance= dbClass.balance;
        String login=dbClass.login;
        System.out.println("MyUserName @screen"+name+userName+MPin+balance+login);
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(login.equals("true")){
                    getDashBoard();
                }else{
                    getLoginPage();
                }
            }
        },2000);

    }
    void getDashBoard(){
        Intent i=new Intent(this,Dashboard.class);
        startActivity(i);
    }
    void getLoginPage(){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
}