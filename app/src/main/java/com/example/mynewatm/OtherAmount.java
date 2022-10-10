package com.example.mynewatm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class OtherAmount extends AppCompatActivity {
    ArrayList<Database> fromJson = new ArrayList<>();
    TextView enterAmount;
    EditText enterDesiredAmount;
    Button confirmInAmt,clearInAmt;
    String language,debitCardNumber,getBalance,newBalance,enteredBalance;
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
        debitCardNumber=a.getStringExtra("debitCardNumber");
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
        confirmInAmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredBalance=String.valueOf(enterDesiredAmount.getText());
                for (int i = 0; i < fromJson.size(); i++) {
                    if (fromJson.get(i).getDebitCardNumber().equals(String.valueOf(debitCardNumber))) {
                        {
                            getBalance=fromJson.get(i).getBalance();
                            deduct();

                        }
                    }
                }
            }
        });
        clearInAmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterDesiredAmount.setText("");
            }
        });
    }
    void deduct(){
        if (enteredBalance.isEmpty()){
            if(language.equals("english")){
                Toast.makeText(this, "Enter the Amount First", Toast.LENGTH_SHORT).show();
            }else if(language.equals("tamil")){
                Toast.makeText(this, "முதலில் தொகையை உள்ளிடவும்", Toast.LENGTH_SHORT).show();
            }
        }else{
        float etBalance=Float.parseFloat(enteredBalance);
        float jsBalance=Float.parseFloat(getBalance);
        if(etBalance>=100){
            if(etBalance%100==0){
                if(jsBalance-etBalance>=0){
                    int newbal= (int) (jsBalance-etBalance);
                    newBalance=Integer.toString(newbal);
                    getCollectCashPage();
                }else{
                    if(language.equals("english")){
                        Toast.makeText(this, "Insufficient Balance.", Toast.LENGTH_SHORT).show();
                    }else if(language.equals("tamil")){
                        Toast.makeText(this, "போதிய இருப்பு இல்லை.", Toast.LENGTH_SHORT).show();
                    }

                }
            }else{
                if(language.equals("english")){
                    Toast.makeText(this, "Enter Amount in Multiples of 100", Toast.LENGTH_SHORT).show();
                }else if(language.equals("tamil")){
                    Toast.makeText(this, "100 இன் பெருக்கல்களில் தொகையை உள்ளிடவும்", Toast.LENGTH_SHORT).show();
                }

            }
        }else{
            if(language.equals("english")){
                Toast.makeText(this, "Minimum Amount should be 100.", Toast.LENGTH_SHORT).show();
            }else if(language.equals("tamil")){
                Toast.makeText(this, "குறைந்தபட்ச தொகை 100 ஆக இருக்க வேண்டும்.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    }
    void getCollectCashPage(){
        Intent i=new Intent(this,CollectCash.class);
        i.putExtra("debitCardNumber",debitCardNumber);
        i.putExtra("language",language);
        i.putExtra("newBalance",newBalance);
        startActivity(i);

    }
}