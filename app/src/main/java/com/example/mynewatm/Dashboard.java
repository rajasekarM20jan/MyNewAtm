package com.example.mynewatm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Dashboard extends AppCompatActivity {
    //variable initialization
    FloatingActionButton logout,accountDetails;
    TextView sendMoney,depositMoney,viewBalance;
    String position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //variable declaration
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        accountDetails=findViewById(R.id.accountDetails);
        logout=findViewById(R.id.logout);
        sendMoney=findViewById(R.id.sendMoney);
        depositMoney=findViewById(R.id.depositMoney);
        viewBalance=findViewById(R.id.viewBalance);
        //creating on click methods for required fields
        accountDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAccountPage(); //gets method to call account page
            }
        });
        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getsendMoneyPage(); // gets method to call Send money page
            }
        });
        depositMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDepositPage(); //gets method to call deposit money page
            }
        });
        viewBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getViewBalancePage(); //Gets method to call view balance page
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getalert(); //method for popping non cancellable alert
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    void getAccountPage(){ //intent for account page
        Intent i=new Intent(this,AccountDetails.class);
        startActivity(i);
    }
    void getsendMoneyPage(){ // intent for send money page
        Intent i=new Intent(this,SendMoney.class);
        startActivity(i);
    }
    void getDepositPage(){ // intent for deposit money page
        Intent i=new Intent(this,DepositMoney.class);
        startActivity(i);
    }
    void getViewBalancePage(){ // intent for view balance page
        Intent i=new Intent(this,ViewBalance.class);
        startActivity(i);
    }
    void getalert(){ // method for alert on logout being clicked
        AlertDialog.Builder a=new AlertDialog.Builder(Dashboard.this);
        a.setTitle(getString(R.string.logout));
        a.setMessage(getString(R.string.logoutMsg));
        a.setCancelable(false);
        a.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        a.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DbClass dbClass=new DbClass(Dashboard.this);
                SharedPreferences sp=getSharedPreferences("MyPref",MODE_PRIVATE);
                String abc=sp.getString("userName","no");
                dbClass.updateLoginStatus(abc,"false");
                dialogInterface.cancel();
                getLoginPage(); // calling method for moving to login page
            }
        });
        a.show();
    }
    void getLoginPage(){ // intent for login page
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
}