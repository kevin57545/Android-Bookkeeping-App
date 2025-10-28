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

public class Signup extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener{
    EditText account,password,passwordCheck;
    Button finish;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefEdit;
    CheckBox showPassword;
    String newAccount,newPassword,newPasswordCheck;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        account=(EditText)findViewById(R.id.settings_deleteDatabase_password);
        password=(EditText)findViewById(R.id.settings_resetPassword_editNew);
        passwordCheck=(EditText)findViewById(R.id.settings_resetPassword_editNewCheck);
        finish=(Button)findViewById(R.id.setting_resetPassword_confirmBtn);
        showPassword = (CheckBox) findViewById(R.id.signup_showPasswordCheckBox);
        showPassword.setOnCheckedChangeListener(this);
        intent=new Intent(this,MainActivity.class);
        prefs = getSharedPreferences("DATA",MODE_PRIVATE);
        prefEdit = prefs.edit();
    }
    public void register(View view){
        newAccount=account.getText().toString();
        newPassword=password.getText().toString();
        newPasswordCheck=passwordCheck.getText().toString();
        if(newAccount.equals("")){
            Toast.makeText(this, getString(R.string.signup_accountToastText),Toast.LENGTH_SHORT).show();
        }else if(newPassword.equals("")){
            Toast.makeText(this, getString(R.string.signup_passwordToastText),Toast.LENGTH_SHORT).show();
        }else if(!(newPassword.equals(newPasswordCheck))){
            Toast.makeText(this, getString(R.string.signup_notMatchToastText),Toast.LENGTH_SHORT).show();
            password.setText("");
            passwordCheck.setText("");
        }else{
            prefEdit.putString("ACCOUNT",account.getText().toString());
            prefEdit.putString("PASSWORD",password.getText().toString());
            prefEdit.putString("Registered","Yes");  //紀錄是否已經註冊
            prefEdit.commit();
            Toast.makeText(this, getString(R.string.signup_successToastText),Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(showPassword.isChecked()){
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            passwordCheck.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        else if(!showPassword.isChecked()){
            password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
            passwordCheck.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
        }

    }
}