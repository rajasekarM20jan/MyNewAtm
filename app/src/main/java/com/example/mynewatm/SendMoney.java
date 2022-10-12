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

public class SendMoney extends AppCompatActivity {
    FloatingActionButton back;
    EditText enterAmountInSendMoney;
    Button buttonInSend;
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
        setContentView(R.layout.activity_send_money);
        dbClass=new DbClass(this);
        dbWriter=dbClass.getWritableDatabase();
        dbReader=dbClass.getReadableDatabase();
        values=new ContentValues();
        back=findViewById(R.id.backInSend);
        enterAmountInSendMoney=findViewById(R.id.enterAmountInSendMoney);
        buttonInSend=findViewById(R.id.buttonInSend);
        Intent a=getIntent();
        position=a.getStringExtra("position");
        pos=Integer.parseInt(position);
        buttonInSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enterAmountInSendMoney.length()!=0){
                    getData();
                }else{
                    enterAmountInSendMoney.setError("Enter the Amount First");
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
        String user=c.getString(1);
        int availableBalance=Integer.parseInt(c.getString(3));
        String st=String.valueOf(enterAmountInSendMoney.getText());
        int sendAmount=Integer.parseInt(st);
        if(availableBalance-sendAmount>=0){
            String newBalance=Integer.toString((availableBalance-sendAmount));
            values.put("balance",newBalance);
            values.put("name",c.getString(0));
            values.put("userName",c.getString(1));
            values.put("MPin",c.getString(2));
            values.put("login",c.getString(4));
            dbWriter.update("UserDetails",values,"userName=?",new String[]{user});
            AlertDialog.Builder a=new AlertDialog.Builder(SendMoney.this);
            a.setTitle("Transaction Successful\n");
            a.setMessage("The amount have been Debited from Your Account");
            a.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    enterAmountInSendMoney.setText(null);
                }
            });a.show();
        }else{
            AlertDialog.Builder a=new AlertDialog.Builder(SendMoney.this);
            a.setTitle("OOPS!\n");
            a.setMessage("Seems You Do Not Have Sufficient Balance");
            a.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    enterAmountInSendMoney.setText(null);
                }
            });a.show();
        }
    }
}