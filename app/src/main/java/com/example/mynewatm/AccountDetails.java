package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import model.UserDetailsDB;

public class AccountDetails extends AppCompatActivity {
    //variable initialization
    FloatingActionButton backButton;
    TextView getName,getUserName,getMPin,getBalance;
    DbClass dbClass;
    SQLiteDatabase dbReader;
    ArrayList<UserDetailsDB> arr=new ArrayList<>();
    String[] data={"name","userName","MPin","balance","login"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // variable declaration
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        backButton=findViewById(R.id.backButton);
        getName=findViewById(R.id.getName);
        getUserName=findViewById(R.id.getUserName);
        getMPin=findViewById(R.id.getMpin);
        getBalance=findViewById(R.id.getBalance);
        dbClass = new DbClass(this);
        dbReader=dbClass.getReadableDatabase();
        SharedPreferences sp=getSharedPreferences("MyPref",MODE_PRIVATE);
        String a=sp.getString("userName","no");
        DbClass dbClass=new DbClass(AccountDetails.this);
        dbClass.getData(a);
        arr.add(new UserDetailsDB(dbClass.name,dbClass.userName,dbClass.MPin,dbClass.balance,dbClass.login));
        getName.setText(arr.get(0).getName());
        getUserName.setText(arr.get(0).getUserName());
        getMPin.setText(arr.get(0).getMPin());
        getBalance.setText("₹ "+arr.get(0).getBalance());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDashboard(); // method to go back to dash board
            }
        });
    }

    @Override
    public void onBackPressed() { //disabling on back pressed
    }

    void getDashboard(){ //intent for dashboard
        Intent i=new Intent(this,Dashboard.class);
        startActivity(i);
    }
}