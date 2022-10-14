package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

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
    DbClass dbClass;
    SharedPreferences sp;
    SQLiteDatabase dbReader;
    String position,userName;
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
        Intent a=getIntent();
        position=a.getStringExtra("position");
        pos=Integer.parseInt(position);
        dbClass = new DbClass(this);
        dbReader=dbClass.getReadableDatabase();
        Cursor c= dbReader.query("UserDetails",data,null,null,null,null,null);
        c.moveToPosition(pos);
        userName=c.getString(1);
        displayBalance.setText(c.getString(3));
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
            //using RawQuery to get all the data of transactions done for the particular user name.
            Cursor c2 = dbReader.rawQuery("SELECT * FROM Transactions WHERE userName =?",new String[]{nameOfUser});
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
                ListAdapter adapter = new ArrayAdapter<>(ViewBalance.this, android.R.layout.simple_list_item_1, list);
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
        i.putExtra("position",position);
        startActivity(i);
    }
}