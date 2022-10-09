package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CollectCash extends AppCompatActivity {
    TextView collectCashTxt;
    Button yes,no;
    String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_cash);
        collectCashTxt=(TextView) findViewById(R.id.collectCashTxt);
        yes=(Button) findViewById(R.id.yes);
        no=(Button) findViewById(R.id.no);
        Intent a=getIntent();
        language=a.getStringExtra("language");
        if(language.equals("tamil")){
            collectCashTxt.setText("பணத்தை பெற்றுக்கொள்ளவும் \n\n\nஇருப்பை சரிபார்க்க விரும்புகிறீர்களா?");
            yes.setText("ஆம்");
            no.setText("இல்லை");
        }else if (language.equals("english")){
            collectCashTxt.setText("Collect Cash \n\n\nDo You wish to check Balance?");
            yes.setText("Yes");
            no.setText("No");
        }
    }
}