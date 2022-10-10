package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class AccountDetails extends AppCompatActivity {
    ArrayList<Database> fromJson = new ArrayList<>();
    TextView enterCredentials;
    EditText enterDebitCard,enterPin;
    Button nextInAccDetails;
    String language,newBalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        enterCredentials=(TextView) findViewById(R.id.enterCredentials);
        enterDebitCard=(EditText) findViewById(R.id.enterDebitCard);
        enterPin=(EditText) findViewById(R.id.enterPin);
        nextInAccDetails=(Button) findViewById(R.id.nextInAccDetails);
        Intent a=getIntent();
        language=a.getStringExtra("language");
        if(language.equals("tamil")){
            enterCredentials.setText("உங்கள் கணக்கு விவரங்களை உள்ளிடவும்");
            enterDebitCard.setHint("டெபிட் கார்டு எண்ணை உள்ளிடவும்");
            enterPin.setHint("பின்னை உள்ளிடவும்");
            nextInAccDetails.setText("அடுத்தது");
        }else if(language.equals("english")){
            enterCredentials.setText("Enter Your Account Details");
            enterDebitCard.setHint("Enter Debit Card Number");
            enterPin.setHint("Enter Pin");
            nextInAccDetails.setText("Next");
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
        nextInAccDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean bool=false;
                for(int i=0;i<fromJson.size();i++){
                    if(fromJson.get(i).getDebitCardNumber().equals(String.valueOf(enterDebitCard.getText()))) {
                        if (fromJson.get(i).getPin().equals(String.valueOf(enterPin.getText()))) {
                            newBalance=fromJson.get(i).getBalance();
                            bool=true;
                            getAccType();
                        }
                    }
                }
                if(!bool){
                    if(language.equals("english")){
                        Toast.makeText(AccountDetails.this, "Enter Valid Credentials", Toast.LENGTH_SHORT).show();
                    } else if(language.equals("tamil")){
                        Toast.makeText(AccountDetails.this, "சரியான விவரங்களை உள்ளிடவும்", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    void getAccType(){
        MediaPlayer myMusic=MediaPlayer.create(this,R.raw.dattebayo);
        myMusic.start();
        Intent i= new Intent(this,AccountType.class);
        i.putExtra("debitCardNumber",String.valueOf(enterDebitCard.getText()));
        i.putExtra("language",language);
        i.putExtra("newBalance",newBalance);
        startActivity(i);
    }
}