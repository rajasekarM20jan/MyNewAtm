package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OtherAmount extends AppCompatActivity {
    TextView enterAmount;
    EditText enterDesiredAmount;
    Button confirmInAmt,clearInAmt;
    String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_amount);
        enterAmount=(TextView) findViewById(R.id.enterAmount);
        enterDesiredAmount=(EditText) findViewById(R.id.enterDesiredAmount);
        confirmInAmt=(Button) findViewById(R.id.confirmInAmt);
        clearInAmt=(Button) findViewById(R.id.clearInAmt);
        Intent a=getIntent();
        language=a.getStringExtra("language");
        if(language.equals("tamil")){
            enterAmount.setText("100 இன் மடங்குகளில் தொகையை உள்ளிடவும்");
            enterDesiredAmount.setHint("தொகையை இங்கே உள்ளிடவும்..");
            confirmInAmt.setText("உறுதி செய்");
            clearInAmt.setText("அழி");
        }else if(language.equals("english")){
            enterAmount.setText("Enter Amount in Multiples Of 100");
            enterDesiredAmount.setHint("enter amount here..");
            confirmInAmt.setText("Confirm");
            clearInAmt.setText("Clear");
        }
    }
}