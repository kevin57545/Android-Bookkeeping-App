package com.example.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsDeleteDatabase extends AppCompatActivity
        implements ToolDeleteDatabaseDialogFragment.InterfaceCommunicator, CompoundButton.OnCheckedChangeListener{
    SQLiteDatabase db;
    private SharedPreferences prefs;
    static final String DB_NAME="AccountBook";
    EditText passwordEdt;
    CheckBox showPassword;
    String password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_delete_database);
        showPassword = (CheckBox) findViewById(R.id.settings_deleteDatabase_showPasswordCheckBox);
        showPassword.setOnCheckedChangeListener(this);
        passwordEdt = (EditText) findViewById(R.id.settings_deleteDatabase_password);
        prefs = getSharedPreferences("DATA",MODE_PRIVATE);
    }

    public void confirmClear(View view){
        showDialog();
    }

    public void showDialog(){
        try{
            String dialogContent = getString(R.string.settings_deleteDatabase_hintDialogText);
            String dialogLeft = getString(R.string.settings_deleteDatabase_OKDialogText);
            String dialogRight = getString(R.string.settings_deleteDatabase_cancelDialogText);

            ToolDeleteDatabaseDialogFragment dialog =
                    ToolDeleteDatabaseDialogFragment.newInstance
                            (dialogContent,dialogLeft,dialogRight);
            dialog.show(getSupportFragmentManager(), "dialog");
        }
        catch (Exception e){}
    }

    @Override
    public void sendDelValue(String value) {
        if(value.equals("DelDB")){
            password = passwordEdt.getText().toString();
            if(!(password.equals(prefs.getString("PASSWORD","")))){
                Toast.makeText(this, getString(R.string.settings_deleteDatabase_errorToastText),Toast.LENGTH_SHORT).show();
                passwordEdt.setText("");
            }else{
                try{
                    deleteDatabase(DB_NAME);
                    Toast.makeText(this, "刪除成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
                catch (Exception e){
                    Toast.makeText(this, "刪除失敗",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void sendDelValue(String returnValue, int returnItemID) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(showPassword.isChecked())
            passwordEdt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        else if(!showPassword.isChecked())
            passwordEdt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
    }
}