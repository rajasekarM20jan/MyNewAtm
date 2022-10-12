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

public class DepositMoney extends AppCompatActivity {
    FloatingActionButton back;
    EditText enterAmountInDeposit;
    Button buttonInDeposit;
    ContentValues values;
    DbClass dbClass;
    String position;
    int pos;
    SQLiteDatabase dbWriter;
    SQLiteDatabase dbReader;
    String[] data={"name","userName","MPin","balance","login"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_money);
        dbClass=new DbClass(this);
        dbWriter=dbClass.getWritableDatabase();
        dbReader=dbClass.getReadableDatabase();
        values=new ContentValues();
        back=findViewById(R.id.backInDeposit);
        enterAmountInDeposit=findViewById(R.id.enterAmountInDeposit);
        buttonInDeposit=findViewById(R.id.buttonInDeposit);
        Intent a=getIntent();
        position=a.getStringExtra("position");
        pos=Integer.parseInt(position);
        System.out.println("Received Position "+position);
        buttonInDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enterAmountInDeposit.length()!=0){
                    getData();
                }else{
                    enterAmountInDeposit.setError("Enter the Amount First");
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDashboard();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    void getDashboard(){
        Intent i=new Intent(this,Dashboard.class);
        i.putExtra("position",position);
        startActivity(i);
    }
    void getData(){
        Cursor c= dbReader.query("UserDetails",data,null,null,null,null,null);
        c.moveToPosition(pos);
        System.out.println("Converted pos"+pos);
        String user=c.getString(1);
        System.out.println("My name"+user);
        int availableBalance=Integer.parseInt(c.getString(3));
        String st=String.valueOf(enterAmountInDeposit.getText());
        int addAmount=Integer.parseInt(st);
            String newBalance=Integer.toString((availableBalance+addAmount));
            values.put("balance",newBalance);
            values.put("name",c.getString(0));
            values.put("userName",c.getString(1));
            values.put("MPin",c.getString(2));
            values.put("login",c.getString(4));
            dbWriter.update("UserDetails",values,"userName=?",new String[]{user});
        AlertDialog.Builder a=new AlertDialog.Builder(DepositMoney.this);
        a.setTitle("Amount Credited\n");
        a.setMessage("The amount have been Credited to Your Account Successfully");
        a.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enterAmountInDeposit.setText(null);
            }
        });a.show();
        }
}