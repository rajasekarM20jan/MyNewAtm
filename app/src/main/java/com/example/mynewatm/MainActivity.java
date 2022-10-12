package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //variable initialization
    EditText etForUserName,etForMPin;
    Button mainPageButton;
    TextView linkForSignup;
    DbClass dbClass;
    SQLiteDatabase dbReader;
    String position;
    String[] data={"name","userName","MPin","balance","login"};
    protected void onCreate(Bundle savedInstanceState) {
        //variable declaration
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etForUserName=findViewById(R.id.etForUserName);
        etForMPin=findViewById(R.id.etForMPin);
        mainPageButton=findViewById(R.id.mainPageButton);
        linkForSignup=findViewById(R.id.linkForSignup);
        dbClass = new DbClass(this);
        dbReader=dbClass.getReadableDatabase();
        linkForSignup.setClickable(true);
        //creating on click listener for required fields
        linkForSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSignUpPage();
            }
        });
        mainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etForUserName.length()!=0){
                    if(etForMPin.length()!=0){
                        getData();
                    }else{
                        etForMPin.setError("Field is Empty");
                    }
                }else{
                    etForUserName.setError("Field is Empty");
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void getSignUpPage(){
        Intent i= new Intent(this,Signup.class);
        startActivity(i);
    }
    //method for reading the data
    void getData(){
        Cursor c= dbReader.query("UserDetails",data,null,null,null,null,null);
        String userName=String.valueOf(etForUserName.getText());
        String mpin=String.valueOf(etForMPin.getText());
        boolean b=false;
        while(c.moveToNext()){
            if (c.getString(1).equals(userName)) {
                if(c.getString(2).equals(mpin)){
                    position=String.valueOf(c.getPosition());
                    System.out.println("UserN"+position);
                    b=true;
                    getDashboard();
                    break;
                }
            }
        }
        if(!b){
            etForUserName.setText(null);
            etForMPin.setText(null);
            etForUserName.setError("Invalid User Credentials");
            etForMPin.setError("Invalid User Credentials");
        }
    }
    //method for intending to dashboard
    void getDashboard(){
        Intent i=new Intent(this,Dashboard.class);
        i.putExtra("position",position);
        System.out.println("Sending Position is"+position);
        startActivity(i);
    }
}