package com.example.mynewatm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import controller.dbControllerDepositMoney;

public class DepositMoney extends AppCompatActivity {
    //variable initialization
    FloatingActionButton back;
    EditText enterAmountInDeposit;
    Button buttonInDeposit;
    ContentValues values,values1;
    DbClass dbClass;
    String position;
    int pos;
    SQLiteDatabase dbWriter;
    SQLiteDatabase dbReader;
    String[] data={"name","userName","MPin","balance","login"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //variable declaration
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_money);
        dbClass=new DbClass(this);
        dbWriter=dbClass.getWritableDatabase();
        dbReader=dbClass.getReadableDatabase();
        values=new ContentValues();
        values1=new ContentValues();
        back=findViewById(R.id.backInDeposit);
        enterAmountInDeposit=findViewById(R.id.enterAmountInDeposit);
        buttonInDeposit=findViewById(R.id.buttonInDeposit);
        Intent a=getIntent();
        position=a.getStringExtra("position");
        pos=Integer.parseInt(position);
        System.out.println("Received Position "+position);

        //creating onclick listeners
        buttonInDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enterAmountInDeposit.length()!=0){
                    getData1(); //calling method getData();
                }else{
                    enterAmountInDeposit.setError(getString(R.string.enterAmtFirst));
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDashboard(); // calling method getDashboard();
            }
        });
    }

    @Override
    public void onBackPressed() { // disabling On back pressed

    }

    public void getDashboard(){ // intent for dashboard
        Intent i=new Intent(this,Dashboard.class);
        i.putExtra("position",position);
        startActivity(i);
    }
    void getData1(){ //logical activities done through controller class
        dbControllerDepositMoney deposit= new dbControllerDepositMoney();
        new dbControllerDepositMoney(DepositMoney.this);
        deposit.deposit(DepositMoney.this);

    }
}