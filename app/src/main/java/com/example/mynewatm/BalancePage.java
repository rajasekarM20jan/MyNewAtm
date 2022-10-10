package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class BalancePage extends AppCompatActivity {
    ArrayList<Database> fromJson = new ArrayList<>();
    TextView balanceTxt;
    Button ok;
    String language, debitCardNumber,newBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_page);
        balanceTxt = (TextView) findViewById(R.id.balanceTxt);
        ok = (Button) findViewById(R.id.ok);
        Intent a = getIntent();
        language = a.getStringExtra("language");
        debitCardNumber = a.getStringExtra("debitCardNumber");
        newBalance=a.getStringExtra("newBalance");
        if (language.equals("tamil")) {
            balanceTxt.setText("உங்கள் இருப்புத் தொகை :");
            ok.setText("சரி");
        } else if (language.equals("english")) {
            balanceTxt.setText("Your Available Balance is :");
            ok.setText("OKAY");
        }
        try {
            getdata();
            click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getdata() throws Exception {
        InputStream getData = getAssets().open("data.json");
        int sizeofJson = getData.available();
        byte[] store = new byte[sizeofJson];
        getData.read(store);
        String abc = new String(store, "UTF-8");
        JSONArray arr = new JSONArray(abc);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);
            String name = (String) obj.get("name");
            String debitCardNumber = (String) obj.get("debitCardNumber");
            String dateOfBirth = (String) obj.get("dateOfBirth");
            String typeOfAccount = (String) obj.get("typeOfAccount");
            String pin = (String) obj.get("pin");
            String balance = (String) obj.get("balance");
            fromJson.add(new Database(name, dateOfBirth, debitCardNumber, typeOfAccount, pin, balance));
        }
    }
    void click() {
        for (int i = 0; i < fromJson.size(); i++) {
            if (fromJson.get(i).getDebitCardNumber().equals(String.valueOf(debitCardNumber))) {
                {
                    /*String bal=fromJson.get(i).getBalance();*/
                    balanceTxt.setText(balanceTxt.getText()+"\n\n\n\n\n\n\n"+newBalance);
                }
            }
        }
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < fromJson.size(); i++) {
                    if (fromJson.get(i).getDebitCardNumber().equals(String.valueOf(debitCardNumber))) {
                        {
                            getThankYou();
                        }
                    }
                }
            }
        });
    }
    void getThankYou(){
        MediaPlayer myMusic=MediaPlayer.create(this,R.raw.arigato);
        myMusic.start();
        Intent i= new Intent(this,ThankYou.class);
        i.putExtra("debitCardNumber",debitCardNumber);
        i.putExtra("language",language);
        startActivity(i);
    }
}