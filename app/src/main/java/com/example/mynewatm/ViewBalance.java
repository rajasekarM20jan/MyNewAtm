package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class ViewBalance extends AppCompatActivity {
    //variable initialization
    FloatingActionButton back;
    TextView displayBalance;
    ListView transactionList;
    SharedPreferences sp;
    SQLiteDatabase dbReader;
    int pos;
    String[] data={"name","userName","MPin","balance","login"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //variable declarations
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_balance);
        back=findViewById(R.id.backInViewBalance);
        displayBalance=findViewById(R.id.displayBalance);
        transactionList=findViewById(R.id.transactionlist);
        DbClass dbClass = new DbClass(this);
        sp=getSharedPreferences("MyPref",MODE_PRIVATE);
        String a=sp.getString("userName","no");
        dbClass.getData(a);
        String name=dbClass.name;
        String myUserName=dbClass.userName;
        String MPin=dbClass.MPin;
        String balance=dbClass.balance;
        String login=dbClass.login;
        System.out.println("MyUserName @ viewBal:"+name+myUserName+MPin+balance+login);
        displayBalance.setText(balance);
        //creating on click listener for floating
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDashboard(); //calling method to go back to dashboard
            }
        });

        //Using try catch for handling error throws
        try {
            //getting value stored in the shared preferences.
            sp=getSharedPreferences("MyPref",MODE_PRIVATE);
            String nameOfUser= sp.getString("userName","");
            System.out.println("SharedPreferenceName: "+nameOfUser);
            DbClass dbClass1=new DbClass(this);
            dbReader=dbClass1.getReadableDatabase();
            //using RawQuery to get all the data of transactions done for the particular user name.
            Cursor c2 = dbReader.rawQuery("SELECT * FROM Transactions WHERE userName =?",new String[]{nameOfUser});
            System.out.println("SharedPreferenceName2: "+nameOfUser);
            ArrayList list=new ArrayList<>();
            while (c2.moveToNext()) {
                System.out.println("My Position:"+c2.getPosition());
                if(c2.getString(2).equals("false")){
                    String debit="Debited : "+c2.getString(1);
                    list.add(debit);
                }else if(c2.getString(2).equals("true")){
                    String credit= "Credited : "+c2.getString(1);
                    list.add(credit);
                }
                System.out.println("MyTransaction list : "+list);
                //creating adapter for list to store arraylist data
                ListAdapter adapter = new ArrayAdapter<>(ViewBalance.this, android.R.layout.simple_list_item_1,list);
                transactionList.setAdapter(adapter);
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() { //disabling on back pressed

    }

    void getDashboard(){ // intent for dashboard page
        Intent i=new Intent(this,Dashboard.class);
        startActivity(i);
    }
}