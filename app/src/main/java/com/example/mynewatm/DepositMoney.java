package com.example.mynewatm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DepositMoney extends AppCompatActivity {
    //variable initialization
    FloatingActionButton back;
    EditText enterAmountInDeposit;
    Button buttonInDeposit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //variable declaration
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_money);
        back=findViewById(R.id.backInDeposit);
        enterAmountInDeposit=findViewById(R.id.enterAmountInDeposit);
        buttonInDeposit=findViewById(R.id.buttonInDeposit);
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
        startActivity(i);
    }
    void getData1(){ //logical activities done through controller class
        DbClass dbClass=new DbClass(DepositMoney.this);
        SharedPreferences sp=getSharedPreferences("MyPref",MODE_PRIVATE);
        String userName=sp.getString("userName","no");
        System.out.println("MyUserName   :  "+userName);
        dbClass.getData(userName);
        String name=dbClass.name;
        String myUserName=dbClass.userName;
        String MPin=dbClass.MPin;
        String balance=dbClass.balance;
        String login=dbClass.login;
        System.out.println("MyUserName : "+name+myUserName+MPin+balance+login);
        int availableBalance=Integer.parseInt(balance);
        String sndAmt=String.valueOf(enterAmountInDeposit.getText());
        int addAmount=Integer.parseInt(sndAmt);
        System.out.println("MyUserName  : bal :"+(availableBalance+addAmount));
        int newBal=availableBalance+addAmount;
        String newBalance=Integer.toString(newBal);
        dbClass=new DbClass(DepositMoney.this);
        dbClass.updateData(name,myUserName,MPin,newBalance,login);
        SimpleDateFormat sdFormat=new SimpleDateFormat("dd/MM/y @ hh:mm:ss");
        Date d=new Date();
        String transaction=sndAmt+"\tOn\t"+sdFormat.format(d);
        String credit="true";
        dbClass.updateTransactions(myUserName,transaction,credit);
        AlertDialog.Builder alert=new AlertDialog.Builder(DepositMoney.this);
        alert.setTitle(R.string.transactionSuccessful);
        alert.setMessage(getString(R.string.creditMsg)+newBalance);
        alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enterAmountInDeposit.setText(null);
                dialogInterface.cancel();
            }
        });
        alert.show();
    }

}