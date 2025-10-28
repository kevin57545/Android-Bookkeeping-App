package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Settings extends AppCompatActivity {
    Intent intentToResetPassword,intentToDatabaseInfo,intentToDeleteDatabase,intentToAboutApp,
            intentToSendInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        intentToResetPassword = new Intent(this,SettingsResetPassword.class);
        intentToDatabaseInfo = new Intent(this,SettingsDatabaseInfo.class);
        intentToDeleteDatabase = new Intent(this,SettingsDeleteDatabase.class);
        intentToAboutApp = new Intent(this,SettingsAboutAPP.class);
        intentToSendInfo = new Intent(this,SettingsSendInfo.class);

    }
    public void resetPassword (View view){
        startActivity(intentToResetPassword);
    }
    public void databaseInfo (View view) {
        startActivity(intentToDatabaseInfo);
    }
    public void clearDatabase (View view){
        startActivity(intentToDeleteDatabase);
    }
    public void aboutAPP (View view){
        startActivity(intentToAboutApp);
    }
    public void sendInfo (View view){
        startActivity(intentToSendInfo);
    }
}