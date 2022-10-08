package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class WithdrawOptions extends AppCompatActivity {
    TextView withdrawOptions;
    Button get500,get1000,get2000,otherAmt;
    String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_options);
        withdrawOptions=(TextView) findViewById(R.id.withdrawOptions);
        get500=(Button) findViewById(R.id.get500);
        get1000=(Button) findViewById(R.id.get1000);
        get2000=(Button) findViewById(R.id.get2000);
        otherAmt=(Button) findViewById(R.id.otherAmt);
        Intent a=getIntent();
        language=a.getStringExtra("language");
        if(language.equals("tamil")){
            withdrawOptions.setText("தொகையைத் தேர்ந்தெடுக்கவும்");
            otherAmt.setText("மற்றவை");
        }else if(language.equals("english")){
            withdrawOptions.setText("Choose the Amount");
            otherAmt.setText("Other");
        }

    }
}