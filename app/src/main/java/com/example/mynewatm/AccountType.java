package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AccountType extends AppCompatActivity {
    TextView selectAccType;
    Button savingsAccount,currentAccount;
    String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type);
        selectAccType=(TextView) findViewById(R.id.selectAccType);
        savingsAccount=(Button) findViewById(R.id.savingsAccount);
        currentAccount=(Button) findViewById(R.id.currentAccount);
        Intent a=getIntent();
        language=a.getStringExtra("language");
        if(language.equals("tamil")){
            selectAccType.setText("உங்கள் கணக்கின் வகையைத் தேர்ந்தெடுக்கவும்");
            savingsAccount.setText("சேமிப்பு கணக்கு");
            currentAccount.setText("நடப்புக் கணக்கு");
        }else if(language.equals("english")){
            selectAccType.setText("Please Select Type of Your Account");
            savingsAccount.setText("Savings Account");
            currentAccount.setText("Current Account");
        }
    }
}