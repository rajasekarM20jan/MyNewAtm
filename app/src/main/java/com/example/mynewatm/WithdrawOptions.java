package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class WithdrawOptions extends AppCompatActivity {
    ArrayList<Database> fromJson = new ArrayList<>();
    TextView withdrawOptions;
    Button get500, get1000, get2000, otherAmt;
    String language,debitCardNumber;
    String getBalance,newBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_options);
        withdrawOptions = (TextView) findViewById(R.id.withdrawOptions);
        get500 = (Button) findViewById(R.id.get500);
        get1000 = (Button) findViewById(R.id.get1000);
        get2000 = (Button) findViewById(R.id.get2000);
        otherAmt = (Button) findViewById(R.id.otherAmt);
        Intent a = getIntent();
        language = a.getStringExtra("language");
        debitCardNumber=a.getStringExtra("debitCardNumber");
        if (language.equals("tamil")) {
            withdrawOptions.setText("தொகையைத் தேர்ந்தெடுக்கவும்");
            otherAmt.setText("மற்றவை");
        } else if (language.equals("english")) {
            withdrawOptions.setText("Choose the Amount");
            otherAmt.setText("Other");
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
        get500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < fromJson.size(); i++) {
                    if (fromJson.get(i).getDebitCardNumber().equals(String.valueOf(debitCardNumber))) {
                        {
                            getBalance=fromJson.get(i).getBalance();
                            try {
                                deduct500();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
        });
        get1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < fromJson.size(); i++) {
                    if (fromJson.get(i).getDebitCardNumber().equals(String.valueOf(debitCardNumber))) {
                        {
                            getBalance=fromJson.get(i).getBalance();
                            try {
                                deduct1000();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
        });
        get2000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < fromJson.size(); i++) {
                    if (fromJson.get(i).getDebitCardNumber().equals(String.valueOf(debitCardNumber))) {
                        {
                            getBalance=fromJson.get(i).getBalance();
                            try {
                                deduct2000();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
        });
        otherAmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOtherAmtPage();
            }
        });
    }
    void deduct500(){
        int a=500;
        int b=Integer.parseInt(getBalance);
        int newBal;
        if(b>=a){
            newBal=b-a;
            String balance=String.valueOf(newBal);
            newBalance=balance;
            getCollectCash();
        }
    }
    void deduct1000(){
        int a=1000;
        int b=Integer.parseInt(getBalance);
        int newBal;
        if(b>=a){
            newBal=b-a;
            String balance=String.valueOf(newBal);
            newBalance=balance;
            getCollectCash();
        }
    }
    void deduct2000(){
        int a=2000;
        int b=Integer.parseInt(getBalance);
        int newBal;
        if(b>=a){
            newBal=b-a;
            newBalance=Integer.toString(newBal);
            getCollectCash();
        }
    }
    void getCollectCash(){
        Intent i=new Intent(this,CollectCash.class);
        i.putExtra("debitCardNumber",debitCardNumber);
        i.putExtra("language",language);
        i.putExtra("newBalance",newBalance);
        startActivity(i);

    }
    void getOtherAmtPage(){
        Intent i=new Intent(this,OtherAmount.class);
        i.putExtra("debitCardNumber",debitCardNumber);
        i.putExtra("language",language);
        startActivity(i);
    }
}