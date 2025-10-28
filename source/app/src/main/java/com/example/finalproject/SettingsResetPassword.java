package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsResetPassword extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener{
    EditText oldPasswordEdt,newPasswordEdt,newPasswordCheckEdt;
    Button confirmBtn;
    CheckBox showPassword;
    String oldPassword,newPassword,newPasswordCheck;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_reset_password);
        oldPasswordEdt = (EditText) findViewById(R.id.settings_deleteDatabase_password);
        newPasswordEdt = (EditText) findViewById(R.id.settings_resetPassword_editNew);
        newPasswordCheckEdt = (EditText) findViewById(R.id.settings_resetPassword_editNewCheck);
        confirmBtn = (Button) findViewById(R.id.setting_resetPassword_confirmBtn);
        showPassword = (CheckBox) findViewById(R.id.settings_deleteDatabase_showPasswordCheckBox);
        showPassword.setOnCheckedChangeListener(this);
        prefs = getSharedPreferences("DATA",MODE_PRIVATE);
        prefEdit = prefs.edit();
    }
    public void confirmChange (View view){
        oldPassword = oldPasswordEdt.getText().toString();
        newPassword = newPasswordEdt.getText().toString();
        newPasswordCheck = newPasswordCheckEdt.getText().toString();
        if(oldPassword.equals(""))
            Toast.makeText(this, getString(R.string.settings_resetPassword_oldToastText),Toast.LENGTH_SHORT).show();
        else if(newPassword.equals(""))
            Toast.makeText(this, getString(R.string.settings_resetPassword_newToastText),Toast.LENGTH_SHORT).show();
        else if(newPasswordCheck.equals(""))
            Toast.makeText(this, getString(R.string.settings_resetPassword_newCheckToastText),Toast.LENGTH_SHORT).show();
        else if(!(newPassword.equals(newPasswordCheck))){
            Toast.makeText(this, getString(R.string.settings_resetPassword_errorNewToastText),Toast.LENGTH_SHORT).show();
            clearEdt();
        }else if(!(oldPassword.equals(prefs.getString("PASSWORD","")))){
            Toast.makeText(this, getString(R.string.settings_resetPassword_errorOldToastText),Toast.LENGTH_SHORT).show();
            clearEdt();
        }else{
            prefEdit.putString("PASSWORD",newPassword);
            prefEdit.putString("Registered","Yes");
            prefEdit.commit();
            Toast.makeText(this, getString(R.string.settings_resetPassword_successToastText),Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    public void clearEdt(){
        oldPasswordEdt.setText("");
        newPasswordEdt.setText("");
        newPasswordCheckEdt.setText("");
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(showPassword.isChecked()){
            oldPasswordEdt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            newPasswordEdt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            newPasswordCheckEdt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }

        else if(!showPassword.isChecked()){
            oldPasswordEdt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
            newPasswordEdt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
            newPasswordCheckEdt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
        }
    }
}