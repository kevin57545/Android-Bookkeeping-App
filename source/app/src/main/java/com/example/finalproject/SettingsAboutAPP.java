package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingsAboutAPP extends AppCompatActivity {
    Button phoneNumberBtn,emailBtn;
    Intent callIntent,emailIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_about_app);
        phoneNumberBtn = (Button) findViewById(R.id.settings_aboutApp_phoneNumber);
        emailBtn = (Button) findViewById(R.id.settings_aboutApp_email);
        callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:+0123456789"));
        emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("s110916013@stu.ntue.edu.tw"));
    }

    public void call(View view){
        try{
            startActivity(callIntent);
        }catch(android.content.ActivityNotFoundException e){
            Toast.makeText(this, getString(R.string.settings_aboutAPP_Contact_phoneToastErrorText),Toast.LENGTH_SHORT).show();
        }
    }
    public void sendEmail(View view){
        try{
            startActivity(emailIntent);
        }catch(android.content.ActivityNotFoundException e){
            Toast.makeText(this, getString(R.string.settings_aboutAPP_Contact_emailToastErrorText),Toast.LENGTH_SHORT).show();
        }
    }
}