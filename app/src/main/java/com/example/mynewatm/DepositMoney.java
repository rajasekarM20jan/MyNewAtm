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
import java.util.Calendar;
import java.util.Date;

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
                    getData(); //calling method getData();
                }else{
                    enterAmountInDeposit.setError(getString(R.string.enter_amt_first));
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

    void getDashboard(){ // intent for dashboard
        Intent i=new Intent(this,Dashboard.class);
        i.putExtra("position",position);
        startActivity(i);
    }
    void getData(){
        //creating this method for depositing the amount into user account details and also for transaction list of the user
        Date d=new Date();
        SimpleDateFormat s=new SimpleDateFormat("dd/MM/y @ hh:mm:ss");
        Cursor c= dbReader.query("UserDetails",data,null,null,null,null,null);
        c.moveToPosition(pos);
        System.out.println("Converted pos"+pos);
        String user=c.getString(1);
        System.out.println("My name"+user);
        int availableBalance=Integer.parseInt(c.getString(3));
        String st=String.valueOf(enterAmountInDeposit.getText());
        if(enterAmountInDeposit.length()!=0){
        int addAmount=Integer.parseInt(st);
            String newBalance=Integer.toString((availableBalance+addAmount));
            values.put("balance",newBalance);
            values.put("name",c.getString(0));
            values.put("userName",c.getString(1));
            values.put("MPin",c.getString(2));
            values.put("login",c.getString(4));
            dbWriter.update("UserDetails",values,"userName=?",new String[]{user});
        values1.put("userName",c.getString(1));
        values1.put("transactions",st+" On "+s.format(d));
        values1.put("credit","true");
        dbWriter.insert("Transactions",null,values1);
        AlertDialog.Builder a=new AlertDialog.Builder(DepositMoney.this);
        a.setTitle(getString(R.string.transaction_successful));
        a.setMessage(getString(R.string.credit_msg));
        a.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enterAmountInDeposit.setText(null);
            }
        });a.show();
        }else{
            enterAmountInDeposit.setError(getString(R.string.enter_amt_first));
        }
    }
}