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
import android.widget.TextView;
import android.widget.Toast;

public class Signup extends AppCompatActivity {
    //Variable Initialization
    EditText signupName,userName,signupPin,confirmPin;
    Button signupPageButton;
    TextView linkForSignIn;
    ContentValues values;
    DbClass dbClass;
    SQLiteDatabase dbWriter,dbReader;
    String[] data={"name","userName","MPin","balance","login"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Variable declaration
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupName=findViewById(R.id.signupName);
        userName=findViewById(R.id.userName);
        signupPin=findViewById(R.id.signupPin);
        confirmPin=findViewById(R.id.confirmPin);
        signupPageButton=findViewById(R.id.signupPageButton);
        linkForSignIn=findViewById(R.id.linkForSignIn);
        dbClass=new DbClass(this);
        dbReader=dbClass.getReadableDatabase();
        //creating with OnclickListener
        signupPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        //Creating link for Sign In
        linkForSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLoginPage();
            }
        });
    }

    @Override
    public void onBackPressed() { //Setting on back pressed as disabled

    }
//Method for pushing data to the table
    void getData(){
        // checking the data for all the inputs whether is empty or not
        if(signupName.length()!=0){
            if(userName.length()!=0){
                if(signupPin.length()==6){
                    if(confirmPin.length()==6){
                        if(String.valueOf(signupPin.getText()).equals(String.valueOf(confirmPin.getText()))){ //checking if the username is already used or not
                            Cursor c= dbReader.query("UserDetails",data,null,null,null,null,null);
                            boolean b=false;
                            while(c.moveToNext()){
                                if (c.getString(1).equals(String.valueOf(userName.getText()))) { //if User name exists shows error with alert dialog
                                    AlertDialog.Builder a=new AlertDialog.Builder(Signup.this);
                                    a.setTitle(getString(R.string.oops));
                                    a.setMessage(getString(R.string.userExists));
                                    a.setCancelable(false);
                                    b=true;
                                    a.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });a.show();
                                }
                            }
                            //if input fields are not empty
                            if(!b){
                                dbWriter=dbClass.getWritableDatabase();
                                values=new ContentValues();
                                values.put("name",String.valueOf(signupName.getText()));
                                values.put("userName",String.valueOf(userName.getText()));
                                values.put("MPin",String.valueOf(signupPin.getText()));
                                values.put("balance","1000");
                                values.put("login","false");
                                dbWriter.insert("UserDetails",null,values);
                                Toast.makeText(this, "Signed Up Successfully", Toast.LENGTH_SHORT).show();
                                getLoginPage();
                            }
                        }else{
                            AlertDialog.Builder a=new AlertDialog.Builder(Signup.this);
                            a.setTitle(getString(R.string.oops));
                            a.setMessage(getString(R.string.mPinMismatch));
                            a.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });a.show();
                        }
                    }else{
                        confirmPin.setError(getString(R.string.emptyField));
                    }
                }else {
                    signupPin.setError(getString(R.string.emptyField));
                }
            }else{
                userName.setError(getString(R.string.emptyField));
            }
        }else{
            signupName.setError(getString(R.string.emptyField));
        }
    }
    //Method for intending to login page
    void getLoginPage(){
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);
    }
}