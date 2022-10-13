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

public class SendMoney extends AppCompatActivity {
    //variable initialization
    FloatingActionButton back;
    EditText enterAmountInSendMoney;
    Button buttonInSend;
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
        setContentView(R.layout.activity_send_money);
        dbClass=new DbClass(this);
        dbWriter=dbClass.getWritableDatabase();
        dbReader=dbClass.getReadableDatabase();
        values=new ContentValues();
        values1=new ContentValues();
        back=findViewById(R.id.backInSend);
        enterAmountInSendMoney=findViewById(R.id.enterAmountInSendMoney);
        buttonInSend=findViewById(R.id.buttonInSend);
        Intent a=getIntent();
        position=a.getStringExtra("position");
        pos=Integer.parseInt(position);
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
        i.putExtra("position",position);
        startActivity(i);
    }
    void getData(){
        //creating method for sending money
        Cursor c= dbReader.query("UserDetails",data,null,null,null,null,null);
        c.moveToPosition(pos);
        String user=c.getString(1);
        int availableBalance=Integer.parseInt(c.getString(3));
        String st=String.valueOf(enterAmountInSendMoney.getText());
        int sendAmount=Integer.parseInt(st);
        SimpleDateFormat s=new SimpleDateFormat("dd/MM/y @ hh:mm:ss");
        Date d=new Date();
        if(availableBalance-sendAmount>=0){ //checking whether the user has enough balance for transaction
            String newBalance=Integer.toString((availableBalance-sendAmount));
            values.put("balance",newBalance);
            values.put("name",c.getString(0));
            values.put("userName",c.getString(1));
            values.put("MPin",c.getString(2));
            values.put("login",c.getString(4));
            dbWriter.update("UserDetails",values,"userName=?",new String[]{user});
            values1.put("userName",c.getString(1));
            values1.put("transactions",st+" On "+s.format(d));
            values1.put("credit","false");
            dbWriter=dbClass.getWritableDatabase();
            dbWriter.insert("Transactions",null,values1);
            AlertDialog.Builder a=new AlertDialog.Builder(SendMoney.this);
            a.setTitle(getString(R.string.transactionSuccessful));
            a.setMessage(getString(R.string.debitMsg));
            a.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    enterAmountInSendMoney.setText(null);
                }
            });a.show();
        }else{
            //setting alert Dialog if user does not have enough balance
            AlertDialog.Builder a=new AlertDialog.Builder(SendMoney.this);
            a.setTitle(getString(R.string.oops));
            a.setMessage(getString(R.string.noEnoughBalance));
            a.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    enterAmountInSendMoney.setText(null);
                }
            });a.show();
        }
    }
}