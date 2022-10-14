package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import model.UserDetailsDB;

public class AccountDetails extends AppCompatActivity {
    //variable initialization
    FloatingActionButton backButton;
    TextView getName,getUserName,getMPin,getBalance;
    DbClass dbClass;
    SQLiteDatabase dbReader;
    String position;
    int pos;
    ArrayList<UserDetailsDB> arr=new ArrayList<>();
    String[] data={"name","userName","MPin","balance","login"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // variable declaration
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        backButton=findViewById(R.id.backButton);
        getName=findViewById(R.id.getName);
        getUserName=findViewById(R.id.getUserName);
        getMPin=findViewById(R.id.getMpin);
        getBalance=findViewById(R.id.getBalance);
        Intent a=getIntent();
        position=a.getStringExtra("position");
        pos=Integer.parseInt(position);
        dbClass = new DbClass(this);
        dbReader=dbClass.getReadableDatabase();
        //running cursor through query
        Cursor c= dbReader.query("UserDetails",data,null,null,null,null,null);
        c.moveToPosition(pos);
        System.out.println("User Name is in "+pos);
        //getting all the fields required using cursor.getString(); and storing it with Model Class
        arr.add(new UserDetailsDB(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4)));
        getName.setText(arr.get(0).getName());
        getUserName.setText(arr.get(0).getUserName());
        getMPin.setText(arr.get(0).getMPin());
        getBalance.setText("₹ "+arr.get(0).getBalance());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDashboard();
            }
        });
    }

    @Override
    public void onBackPressed() { //disabling on back pressed
    }

    void getDashboard(){ //intent for dashboard
        Intent i=new Intent(this,Dashboard.class);
        i.putExtra("position",position);
        startActivity(i);
    }
}