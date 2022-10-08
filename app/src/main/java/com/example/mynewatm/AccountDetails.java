package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AccountDetails extends AppCompatActivity {
    TextView enterCredentials;
    EditText enterDebitCard,enterPin;
    Button nextInAccDetails;
    String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        enterCredentials=(TextView) findViewById(R.id.enterCredentials);
        enterDebitCard=(EditText) findViewById(R.id.enterDebitCard);
        enterPin=(EditText) findViewById(R.id.enterPin);
        nextInAccDetails=(Button) findViewById(R.id.nextInAccDetails);
        Intent a=getIntent();
        language=a.getStringExtra("language");
        if(language.equals("tamil")){
            enterCredentials.setText("உங்கள் கணக்கு விவரங்களை உள்ளிடவும்");
            enterDebitCard.setHint("டெபிட் கார்டு எண்ணை உள்ளிடவும்");
            enterPin.setHint("பின்னை உள்ளிடவும்");
            nextInAccDetails.setText("அடுத்தது");
        }else if(language.equals("english")){
            enterCredentials.setText("Enter Your Account Details");
            enterDebitCard.setHint("Enter Debit Card Number");
            enterPin.setHint("Enter Pin");
            nextInAccDetails.setText("Next");
        }
        nextInAccDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAccType();
            }
        });

    }
    void getAccType(){
        Intent i= new Intent(this,AccountType.class);
        i.putExtra("language",language);
        startActivity(i);
    }
}