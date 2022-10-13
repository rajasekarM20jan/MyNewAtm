package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import controller.dbControllerMainActivity;

public class MainActivity extends AppCompatActivity {
    //variable initialization
    public EditText etForUserName;
    public EditText etForMPin;
    Button mainPageButton;
    TextView linkForSignup;
    protected void onCreate(Bundle savedInstanceState) {
        //variable declaration
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etForUserName=findViewById(R.id.etForUserName);
        etForMPin=findViewById(R.id.etForMPin);
        mainPageButton=findViewById(R.id.mainPageButton);
        linkForSignup=findViewById(R.id.linkForSignup);
        linkForSignup.setClickable(true);
        //creating on click listener for required fields
        linkForSignup.setOnClickListener(new View.OnClickListener() { //Setting Onclick Listener for connecting with the signUp page.
            @Override
            public void onClick(View view) {
                getSignUpPage();
            }
        });
        mainPageButton.setOnClickListener(new View.OnClickListener() {  //Setting onclick Listener for connecting with dashBoard Page
            @Override
            public void onClick(View view) {
                if(etForUserName.length()!=0){
                    if(etForMPin.length()!=0){
                        getData1();
                    }else{
                        etForMPin.setError(getString(R.string.empty_field)); // Setting Error Message if MPin is Empty
                    }
                }else{
                    etForUserName.setError(getString(R.string.empty_field)); //Setting error message if UserName is Empty
                }
            }
        });
    }

    @Override
    public void onBackPressed() { //Using On back Pressed for closing the application entirely with the help of finishAffinity();

        this.finishAffinity();
    }
    //method for intending to signUp Page
    void getSignUpPage(){
        Intent i= new Intent(this,Signup.class);
        startActivity(i);
    }
    //method for intending to dashboard
    public void getDashboard(String position){
        Intent i=new Intent(this,Dashboard.class);
        i.putExtra("position",position);
        System.out.println("Sending Position is"+position);
        startActivity(i);
    }
    //method for defining the Error..
    public void getError(){
        etForUserName.setText(null);
        etForMPin.setText(null);
        etForUserName.setError("Invalid User Credentials");
        etForMPin.setError("Invalid User Credentials");
    }
    //Calling the function of dbController for Main activity to perform the logical operations and get result according to the same.
    void getData1(){
        dbControllerMainActivity db=new dbControllerMainActivity();
        db.loginActivity(MainActivity.this);
        new dbControllerMainActivity(MainActivity.this);
    }
}