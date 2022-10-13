package controller;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mynewatm.DbClass;
import com.example.mynewatm.MainActivity;
import com.example.mynewatm.R;

public class dbControllerMainActivity {
    DbClass dbClass;
    SQLiteDatabase dbReader,dbWriter;
    ContentValues values;
    String[] data={"name","userName","MPin","balance","login"};
    MainActivity UserVerification;
    EditText etForUserName,etForMPin;
    Button mainPageButton;
    TextView linkForSignup;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    String position;
    public dbControllerMainActivity(){

    }
    //creating parameterized constructor for initializing data
    public dbControllerMainActivity(MainActivity mainActivity){
        this.UserVerification =mainActivity;
        etForUserName= UserVerification.findViewById(R.id.etForUserName);
        etForMPin= UserVerification.findViewById(R.id.etForMPin);
        mainPageButton= UserVerification.findViewById(R.id.mainPageButton);
        linkForSignup= UserVerification.findViewById(R.id.linkForSignup);
        sp= UserVerification.getSharedPreferences("MyPref",MODE_PRIVATE);
        edit= sp.edit();
        linkForSignup.setClickable(true);
    }
    //creating method for verification in login
    public void loginActivity(MainActivity mainActivity){
        this.UserVerification =mainActivity;
        dbClass=new DbClass(UserVerification);
        dbReader=dbClass.getReadableDatabase();
        Cursor c= dbReader.query("UserDetails",data,null,null,null,null,null);
        String userName=String.valueOf(UserVerification.etForUserName.getText());
        String mpin=String.valueOf(UserVerification.etForMPin.getText());
        sp= UserVerification.getSharedPreferences("MyPref",MODE_PRIVATE);
        edit= sp.edit();
        boolean b=false;
        while(c.moveToNext()){ //checks with the database if user name and pin are existing
            if (c.getString(1).equals(userName)) {
                if(c.getString(2).equals(mpin)){
                    position=String.valueOf(c.getPosition());
                    edit.putString("userName",userName);
                    edit.commit();
                    b=true;
                    UserVerification.getDashboard(position);
                    break;
                }
            }
        }
        if(!b){ //if user is not existing it is passed to get Error method of main activity.
           UserVerification.getError();
        }
    }
}
