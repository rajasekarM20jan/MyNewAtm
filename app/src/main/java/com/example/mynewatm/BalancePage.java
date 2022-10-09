package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class BalancePage extends AppCompatActivity {
    TextView balanceTxt;
    Button ok;
    String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_page);
        balanceTxt=(TextView) findViewById(R.id.balanceTxt);
        ok=(Button) findViewById(R.id.ok);
        Intent a=getIntent();
        language=a.getStringExtra("language");
        if(language.equals("tamil")){
            balanceTxt.setText("உங்கள் இருப்புத் தொகை :");
            ok.setText("சரி");
        }else if(language.equals("english")){
            balanceTxt.setText("Your Available Balance is :");
            ok.setText("OKAY");
        }
    }
}