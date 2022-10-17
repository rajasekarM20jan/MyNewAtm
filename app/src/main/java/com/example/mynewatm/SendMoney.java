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
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SendMoney extends AppCompatActivity {
    //variable initialization
    FloatingActionButton back;
    EditText enterAmountInSendMoney;
    Button buttonInSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //variable declaration
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        back=findViewById(R.id.backInSend);
        enterAmountInSendMoney=findViewById(R.id.enterAmountInSendMoney);
        buttonInSend=findViewById(R.id.buttonInSend);
        //creating on click listener for back button
        buttonInSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enterAmountInSendMoney.length()!=0){
                    getData();
                }else{
                    enterAmountInSendMoney.setError(getString(R.string.enterAmtFirst));
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDashboard(); //calling method for going back to dashboard
            }
        });
    }

    @Override
    public void onBackPressed() { //disabling on back pressed

    }

    void getDashboard(){
        Intent i=new Intent(this,Dashboard.class);
        startActivity(i);
    }
    void getData() {
        DbClass dbClass=new DbClass(SendMoney.this);
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
        String sndAmt=String.valueOf(enterAmountInSendMoney.getText());
        int sendAmount=Integer.parseInt(sndAmt);
        if(availableBalance-sendAmount>=0){
            System.out.println("MyUserName  : bal :"+(availableBalance-sendAmount));
            int newBal=availableBalance-sendAmount;
            String newBalance=Integer.toString(newBal);
            dbClass=new DbClass(SendMoney.this);
            dbClass.updateData(name,myUserName,MPin,newBalance,login);
            SimpleDateFormat sdFormat=new SimpleDateFormat("dd/MM/y @ hh:mm:ss");
            Date d=new Date();
            String transaction=sndAmt+"\tOn\t"+sdFormat.format(d);
            String credit="false";
            dbClass.updateTransactions(myUserName,transaction,credit);
            AlertDialog.Builder alert=new AlertDialog.Builder(SendMoney.this);
            alert.setTitle(R.string.transactionSuccessful);
            alert.setMessage(getString(R.string.debitMsg)+newBalance);
            alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    enterAmountInSendMoney.setText(null);
                    dialogInterface.cancel();
                }
            });
            alert.show();
        }
        else{
            AlertDialog.Builder alert=new AlertDialog.Builder(SendMoney.this);
            alert.setTitle(R.string.oops);
            alert.setMessage(getString(R.string.noEnoughBalance));
            alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    enterAmountInSendMoney.setText(null);
                    dialogInterface.cancel();
                }
            });alert.show();
        }
    }
}