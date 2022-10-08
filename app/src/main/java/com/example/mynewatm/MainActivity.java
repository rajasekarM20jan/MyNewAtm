package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button english,tamil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        english=(Button) findViewById(R.id.setEnglish);
        tamil=(Button) findViewById(R.id.setTamil);
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAccDetailsEnglish();
            }
        });
        tamil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAccDetailsTamil();
            }
        });
    }
    void getAccDetailsEnglish(){
        Intent i=new Intent(this,AccountDetails.class);
        i.putExtra("language","english");
        startActivity(i);
    }
    void getAccDetailsTamil(){
        Intent i=new Intent(this,AccountDetails.class);
        i.putExtra("language","tamil");
        startActivity(i);
    }
}