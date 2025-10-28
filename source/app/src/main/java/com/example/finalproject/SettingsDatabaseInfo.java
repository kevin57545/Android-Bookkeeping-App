package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingsDatabaseInfo extends AppCompatActivity {
    SQLiteDatabase db;
    static final String DB_NAME="AccountBook";
    static final String TB_NAME="List";
    TextView showPathTxv,showSizeTxv,showBoundTxv;
    Intent intentToDeleteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_database_info);

        showPathTxv = (TextView) findViewById(R.id.settings_databaseInfo_showPath);
        showSizeTxv = (TextView) findViewById(R.id.settings_databaseInfo_showSize);
        showBoundTxv = (TextView) findViewById(R.id.settings_databaseInfo_showBound);

        intentToDeleteDatabase = new Intent(this,SettingsDeleteDatabase.class);

        databaseInfo();
    }
    public void databaseInfo(){
        db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null);
        try{//取得料庫資訊並顯示出來
            showPathTxv.setText(" "+db.getPath());
            showSizeTxv.setText(" "+db.getPageSize()+" Bytes");
            showBoundTxv.setText(" "+db.getMaximumSize()+" Bytes\n");
        } catch (Exception e){
        }
        db.close();
    }
    public void clearDatabase(View view){
        startActivity(intentToDeleteDatabase);
    }

}