package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AccountDetails extends AppCompatActivity {
    FloatingActionButton backButton;
    TextView getName,getUserName,getMPin,getBalance;
    DbClass dbClass;
    SQLiteDatabase dbReader;
    String position;
    int pos;
    String[] data={"name","userName","MPin","balance","login"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        Cursor c= dbReader.query("UserDetails",data,null,null,null,null,null);
        c.moveToPosition(pos);
        getName.setText(c.getString(0));
        getUserName.setText(c.getString(1));
        getMPin.setText(c.getString(2));
        getBalance.setText(c.getString(3));
        backButton.setOnClickListener(new View.OnClickListener() {
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
}