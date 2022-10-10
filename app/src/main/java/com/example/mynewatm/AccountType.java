package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class AccountType extends AppCompatActivity {
    ArrayList<Database> fromJson = new ArrayList<>();
    TextView selectAccType;
    Button savingsAccount,currentAccount;
    String language,debitCardNumber,newBalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type);
        selectAccType=(TextView) findViewById(R.id.selectAccType);
        savingsAccount=(Button) findViewById(R.id.savingsAccount);
        currentAccount=(Button) findViewById(R.id.currentAccount);
        Intent a=getIntent();
        language=a.getStringExtra("language");
        debitCardNumber=a.getStringExtra("debitCardNumber");
        newBalance=a.getStringExtra("newBalance");
        if(language.equals("tamil")){
            selectAccType.setText("உங்கள் கணக்கின் வகையைத் தேர்ந்தெடுக்கவும்");
            savingsAccount.setText("சேமிப்பு கணக்கு");
            currentAccount.setText("நடப்புக் கணக்கு");
        }else if(language.equals("english")){
            selectAccType.setText("Please Select Type of Your Account");
            savingsAccount.setText("Savings Account");
            currentAccount.setText("Current Account");
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
        savingsAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                boolean b=false;
                for (int i = 0; i < fromJson.size(); i++) {
                    if (fromJson.get(i).getDebitCardNumber().equals(debitCardNumber)){
                        if (fromJson.get(i).getTypeOfAccount().equals("SavingsAccount")) {
                            b=true;
                            getintent();
                        }
                    }
                }if(!b){
                    if(language.equals("english")){
                        Toast.makeText(AccountType.this, "Select Only your Account Type", Toast.LENGTH_SHORT).show();
                    }else if(language.equals("tamil")){
                        Toast.makeText(AccountType.this, "உங்கள் கணக்கு வகையை மட்டும் தேர்ந்தெடுக்கவும்", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        currentAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b=false;
                for (int i = 0; i < fromJson.size(); i++) {
                    if (fromJson.get(i).getDebitCardNumber().equals(debitCardNumber)){
                        if (fromJson.get(i).getTypeOfAccount().equals("CurrentAccount")) {
                            b=true;
                            getintent();
                        }
                    }
                }if(!b){
                    if(language.equals("english")){
                        Toast.makeText(AccountType.this, "Select Only your Account Type", Toast.LENGTH_SHORT).show();
                    }else if(language.equals("tamil")){
                        Toast.makeText(AccountType.this, "உங்கள் கணக்கு வகையை மட்டும் தேர்ந்தெடுக்கவும்", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    void getintent(){
        Intent i=new Intent(this,Options.class);
        i.putExtra("debitCardNumber",debitCardNumber);
        i.putExtra("language",language);
        i.putExtra("newBalance",newBalance);
        startActivity(i);
    }
}
