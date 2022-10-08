package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Options extends AppCompatActivity {
    TextView chooseYourOption;
    Button withdraw,checkBalance;
    String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        chooseYourOption=(TextView) findViewById(R.id.chooseYourOption);
        withdraw=(Button) findViewById(R.id.withdraw);
        checkBalance=(Button) findViewById(R.id.checkBalance);
        Intent a=getIntent();
        language=a.getStringExtra("language");
        if(language.equals("tamil")){
            chooseYourOption.setText("கீழே உள்ள ஏதேனும் ஒரு விருப்பத்தைத் தேர்ந்தெடுக்கவும்");
            withdraw.setText("தொகை பெறுதல்");
            checkBalance.setText("இருப்பு விசாரணை");
        }else if(language.equals("english")){
            chooseYourOption.setText("Select Any Option Below");
            withdraw.setText("withdraw");
            checkBalance.setText("Check Balance");
        }
    }
}