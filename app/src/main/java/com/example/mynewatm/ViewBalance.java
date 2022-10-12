package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewBalance extends AppCompatActivity {
    FloatingActionButton back;
    TextView displayBalance;
    ListView transactionList;
    DbClass dbClass;
    SQLiteDatabase dbReader;
    String position;
    int pos;
    String[] data={"name","userName","MPin","balance","login"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        displayBalance.setText(c.getString(3));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDashboard();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void getDashboard(){
        Intent i=new Intent(this,Dashboard.class);
        i.putExtra("position",position);
        startActivity(i);
    }
}