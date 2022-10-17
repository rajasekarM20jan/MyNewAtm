package com.example.mynewatm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.dbControllerMainActivity;

public class MainActivity extends AppCompatActivity {
    //variable initialization
    public EditText etForUserName;
    public EditText etForMPin;
    Button mainPageButton;
    TextView linkForSignup;
    DbClass dbclass;
    protected void onCreate(Bundle savedInstanceState) {
        //variable declaration
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etForUserName=findViewById(R.id.etForUserName);
        etForMPin=findViewById(R.id.etForMPin);
        mainPageButton=findViewById(R.id.mainPageButton);
        linkForSignup=findViewById(R.id.linkForSignup);
        linkForSignup.setClickable(true);
        //creating on click listener for required fields
        linkForSignup.setOnClickListener(new View.OnClickListener() { //Setting Onclick Listener for connecting with the signUp page.
            @Override
            public void onClick(View view) {
                getSignUpPage();
            }
        });
        mainPageButton.setOnClickListener(new View.OnClickListener() {  //Setting onclick Listener for connecting with dashBoard Page
            @Override
            public void onClick(View view) {
                if(etForUserName.length()!=0){
                    String validate="[a-zA-Z0-9_]{3,16}$";
                    Pattern checkPass= Pattern.compile(validate);
                    Matcher compare = checkPass.matcher(String.valueOf(etForUserName.getText()));
                    boolean b=compare.matches();
                    if(b) {

                        if(etForMPin.length()!=0){
                        /*dbclass=new DbClass(MainActivity.this);
                        dbclass.getDataForLogin(String.valueOf(etForUserName.getText()),String.valueOf(etForMPin.getText()));*/
                            getData1();
                        }else{
                            etForMPin.setError(getString(R.string.emptyField)); // Setting Error Message if MPin is Empty
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "Enter a Valid userName", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    etForUserName.setError(getString(R.string.emptyField)); //Setting error message if UserName is Empty
                }
            }
        });
    }

    @Override
    public void onBackPressed() { //Using On back Pressed for closing the application entirely with the help of finishAffinity();

        this.finishAffinity();
    }
    //method for intending to signUp Page
    void getSignUpPage(){
        Intent i= new Intent(this,Signup.class);
        startActivity(i);
    }
    //method for intending to dashboard
    public void getDashboard(String position){
        Intent i=new Intent(this,Dashboard.class);
        i.putExtra("position",position);
        System.out.println("Sending Position is"+position);
        startActivity(i);
    }
    //method for defining the Error..
    public void getError(){
        etForUserName.setText(null);
        etForMPin.setText(null);
        etForUserName.setError("Invalid User Credentials");
        etForMPin.setError("Invalid User Credentials");
    }
    //Calling the function of dbController for Main activity to perform the logical operations and get result according to the same.
    void getData1(){
        dbControllerMainActivity db=new dbControllerMainActivity();
        db.loginActivity(MainActivity.this);
        new dbControllerMainActivity(MainActivity.this);
    }
    public void alreadyLoggedIn(String name,String userName,String MPin,String balance,String login){
        AlertDialog.Builder alert= new AlertDialog.Builder(MainActivity.this);
        alert.setTitle(R.string.alreadyLoggedIn);
        alert.setMessage(R.string.alreadyLoggedInText);
        alert.setCancelable(false);
        alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DbClass dbClass=new DbClass(MainActivity.this);
                SQLiteDatabase dbWriter=dbClass.getWritableDatabase();
                ContentValues values= new ContentValues();
                values.put("userName",userName);
                values.put("name",name);
                values.put("login","false");
                values.put("balance",balance);
                values.put("MPin",MPin);
                dbWriter.update("UserDetails",values,"userName=?",new String[]{userName});
                dialogInterface.cancel();
            }
        });alert.show();
    }


   /* public void sendToDbClass(String name,String userName,String MPin,String balance,String login){
        System.out.println("MyTr"+name+userName+MPin+balance+login);
        dbclass=new DbClass(MainActivity.this);
        dbclass.updateData(name,userName,MPin,balance,login);
    }
*/


}