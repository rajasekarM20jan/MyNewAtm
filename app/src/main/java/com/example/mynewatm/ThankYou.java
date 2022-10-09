package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThankYou extends AppCompatActivity {
    TextView thankYouTxt;
    Button done;
    String language,debitCardNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        thankYouTxt=(TextView) findViewById(R.id.thankYouTxt);
        done=(Button) findViewById(R.id.done);
        Intent a=getIntent();
        language=a.getStringExtra("language");
        debitCardNumber=a.getStringExtra("debitCardNumber");
        if(language.equals("tamil")){
            thankYouTxt.setText("நன்றி!!!");
            done.setText("முடிந்தது");
        }else if(language.equals("english")){
            thankYouTxt.setText("Thank You!!!");
            done.setText("Done");
        }
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHome();
            }
        });
    }
    void getHome(){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
}