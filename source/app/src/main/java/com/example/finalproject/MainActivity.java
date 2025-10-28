package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener {
    EditText account,password;
    Button login,signup;
    Intent intent,intent1;
    private SharedPreferences prefs;
    CheckBox showPassword;
    String userAccount,userPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account = (EditText)findViewById(R.id.settings_deleteDatabase_password);
        password = (EditText)findViewById(R.id.settings_resetPassword_editNew);
        login = (Button)findViewById(R.id.loginBtn);
        signup = (Button)findViewById(R.id.setting_resetPassword_confirmBtn);
        showPassword = (CheckBox) findViewById(R.id.login_showPasswordCheckBox);
        showPassword.setOnCheckedChangeListener(this);
        intent = new Intent(this,Signup.class);
        intent1 = new Intent(this,HomePage.class);
        prefs = getSharedPreferences("DATA",MODE_PRIVATE);
        if(prefs.getString("Registered","").equals("Yes"))
            signup.setVisibility(View.GONE);
        else Toast.makeText(this, getString(R.string.login_signupToastText),Toast.LENGTH_SHORT).show();
    }
    public void login(View view){
        if(!prefs.getString("Registered","").equals("Yes")){
            Toast.makeText(this, getString(R.string.login_signupToastText),Toast.LENGTH_SHORT).show();
            return;
        }
        userAccount=account.getText().toString();
        userPassword=password.getText().toString();
        if(userAccount.equals("")||userPassword.equals("")){
            Toast.makeText(this, getString(R.string.login_emptyToastText),Toast.LENGTH_SHORT).show();
        }else if(userAccount.equals(prefs.getString("ACCOUNT",""))&&
                userPassword.equals(prefs.getString("PASSWORD",""))){
            Toast.makeText(this, getString(R.string.login_successToastText),Toast.LENGTH_SHORT).show();
            startActivity(intent1);
        }else{
            Toast.makeText(this, getString(R.string.login_errorToastText),Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View view){
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(showPassword.isChecked())
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        else if(!showPassword.isChecked())
            password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
        }

}