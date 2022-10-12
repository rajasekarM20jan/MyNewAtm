package com.example.mynewatm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Dashboard extends AppCompatActivity {
    FloatingActionButton logout,accountDetails;
    TextView sendMoney,depositMoney,viewBalance;
    String position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        accountDetails=findViewById(R.id.accountDetails);
        logout=findViewById(R.id.logout);
        sendMoney=findViewById(R.id.sendMoney);
        depositMoney=findViewById(R.id.depositMoney);
        viewBalance=findViewById(R.id.viewBalance);
        Intent a =getIntent();
        position=a.getStringExtra("position");
        accountDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAccountPage();
            }
        });
        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getsendMoneyPage();
            }
        });
        depositMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdepositPage();
            }
        });
        viewBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getViewBalancePage();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getalert();
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    void getAccountPage(){
        Intent i=new Intent(this,AccountDetails.class);
        i.putExtra("position",position);
        System.out.println("Sending Position is"+position);
        startActivity(i);
    }
    void getsendMoneyPage(){
        Intent i=new Intent(this,SendMoney.class);
        i.putExtra("position",position);
        System.out.println("Sending Position is"+position);
        startActivity(i);
    }
    void getdepositPage(){
        Intent i=new Intent(this,DepositMoney.class);
        i.putExtra("position",position);
        System.out.println("Sending Position is"+position);
        startActivity(i);
    }
    void getViewBalancePage(){
        Intent i=new Intent(this,ViewBalance.class);
        i.putExtra("position",position);
        System.out.println("Sending Position is"+position);
        startActivity(i);
    }
    void getalert(){
        AlertDialog.Builder a=new AlertDialog.Builder(Dashboard.this);
        a.setTitle(getString(R.string.logout));
        a.setMessage(getString(R.string.logout_msg));
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
                getLoginPage();
            }
        });
        a.show();
    }
    void getLoginPage(){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
}