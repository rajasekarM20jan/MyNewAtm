package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText etForUserName,etForMPin;
    Button mainPageButton;
    TextView linkForSignup;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etForUserName=findViewById(R.id.etForUserName);
        etForMPin=findViewById(R.id.etForMPin);
        mainPageButton=findViewById(R.id.mainPageButton);
        linkForSignup=findViewById(R.id.linkForSignup);
        linkForSignup.setClickable(true);
        linkForSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSignUpPage();
            }
        });

    }
    void getSignUpPage(){
        Intent i= new Intent(this,SendMoney.class);
        startActivity(i);
    }
}