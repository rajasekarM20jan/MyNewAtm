package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class Options extends AppCompatActivity {
    ArrayList<Database> fromJson = new ArrayList<>();
    TextView chooseYourOption;
    Button withdraw,checkBalance;
    String language,debitCardNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        chooseYourOption=(TextView) findViewById(R.id.chooseYourOption);
        withdraw=(Button) findViewById(R.id.withdraw);
        checkBalance=(Button) findViewById(R.id.checkBalance);
        Intent a=getIntent();
        language=a.getStringExtra("language");
        debitCardNumber=a.getStringExtra("debitCardNumber");
        if(language.equals("tamil")){
            chooseYourOption.setText("கீழே உள்ள ஏதேனும் ஒரு விருப்பத்தைத் தேர்ந்தெடுக்கவும்");
            withdraw.setText("தொகை பெறுதல்");
            checkBalance.setText("இருப்பு விசாரணை");
        }else if(language.equals("english")){
            chooseYourOption.setText("Select Any Option Below");
            withdraw.setText("withdraw");
            checkBalance.setText("Check Balance");
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
            String name=(String) obj.get("name");
            String debitCardNumber=(String) obj.get("debitCardNumber");
            String dateOfBirth=(String) obj.get("dateOfBirth");
            String typeOfAccount=(String) obj.get("typeOfAccount");
            String pin=(String) obj.get("pin");
            String balance=(String) obj.get("balance");
            fromJson.add(new Database(name,dateOfBirth,debitCardNumber,typeOfAccount,pin,balance));
        }
    }
    void click(){
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                for (int i = 0; i < fromJson.size(); i++) {
                    if (fromJson.get(i).getDebitCardNumber().equals(String.valueOf(debitCardNumber))) {
                        {
                            getWithdrawOptions();
                        }
                    }
                }
            }
        });
        checkBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < fromJson.size(); i++) {
                    if (fromJson.get(i).getDebitCardNumber().equals(String.valueOf(debitCardNumber))) {
                        {
                            getBalancePage();
                        }
                    }
                }
            }
        });
    }
    void getWithdrawOptions(){
        Intent i=new Intent(this,WithdrawOptions.class);
        i.putExtra("language",language);
        i.putExtra("debitCardNumber",debitCardNumber);
        startActivity(i);
    }
    void getBalancePage(){
        Intent i=new Intent(this,BalancePage.class);
        i.putExtra("language",language);
        i.putExtra("debitCardNumber",debitCardNumber);
        startActivity(i);
    }
}