package controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.mynewatm.DbClass;
import com.example.mynewatm.DepositMoney;
import com.example.mynewatm.MainActivity;
import com.example.mynewatm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class dbControllerDepositMoney {
    FloatingActionButton back;
    EditText enterAmountInDeposit;
    Button buttonInDeposit;
    DepositMoney userAccess;
    ContentValues values,values1;
    DbClass dbClass;
    String position;
    int pos;
    SQLiteDatabase dbWriter;
    SQLiteDatabase dbReader;
    String[] data={"name","userName","MPin","balance","login"};
    public dbControllerDepositMoney(){

    }
    public dbControllerDepositMoney(DepositMoney depositMoney){
        this.userAccess=depositMoney;
        dbClass=new DbClass(userAccess);
        dbWriter=dbClass.getWritableDatabase();
        dbReader=dbClass.getReadableDatabase();
        values=new ContentValues();
        values1=new ContentValues();
        back=userAccess.findViewById(R.id.backInDeposit);
        enterAmountInDeposit=userAccess.findViewById(R.id.enterAmountInDeposit);
        buttonInDeposit=userAccess.findViewById(R.id.buttonInDeposit);
        Intent a=userAccess.getIntent();
        position=a.getStringExtra("position");
        pos=Integer.parseInt(position);
    }
    public void deposit(DepositMoney depositMoney){
        this.userAccess=depositMoney;
        dbClass=new DbClass(userAccess);
        enterAmountInDeposit=userAccess.findViewById(R.id.enterAmountInDeposit);
        //creating this method for depositing the amount into user account details and also for transaction list of the user
        Date d=new Date();
        SharedPreferences sp= depositMoney.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String user= sp.getString("userName","no");
        dbReader=dbClass.getReadableDatabase();
        SimpleDateFormat s=new SimpleDateFormat("dd/MM/y @ hh:mm:ss");
        Cursor c= dbReader.rawQuery("SELECT * FROM UserDetails WHERE userName=?",new String[]{user});
        c.moveToNext();
        System.out.println("User Name is"+user);
        int availableBalance=Integer.parseInt(c.getString(3));
        String st=String.valueOf(enterAmountInDeposit.getText());
        System.out.println("MyT"+st);
        if(enterAmountInDeposit.length()!=0){
            int addAmount=Integer.parseInt(st);
            String newBalance=Integer.toString((availableBalance+addAmount));
            values=new ContentValues();
            c.moveToPosition(pos);
            values.put("balance",newBalance);
            values.put("name",c.getString(0));
            values.put("userName",c.getString(1));
            values.put("MPin",c.getString(2));
            values.put("login",c.getString(4));
            dbWriter= dbClass.getWritableDatabase();
            dbWriter.update("UserDetails",values,"userName=?",new String[]{user});
            values1=new ContentValues();
            values1.put("userName",c.getString(1));
            values1.put("transactions",st+" On "+s.format(d));
            values1.put("credit","true");
            dbWriter=dbClass.getWritableDatabase();
            dbWriter.insert("Transactions",null,values1);
            AlertDialog.Builder a=new AlertDialog.Builder(userAccess);
            a.setTitle(userAccess.getString(R.string.transactionSuccessful));
            a.setMessage(userAccess.getString(R.string.creditMsg));
            a.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    enterAmountInDeposit.setText(null);
                }
            });a.show();
        }else{
            enterAmountInDeposit.setError(userAccess.getString(R.string.enterAmtFirst));
        }
    }
}
