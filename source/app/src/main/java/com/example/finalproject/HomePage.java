package com.example.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class HomePage extends AppCompatActivity
        implements ToolExitDialogFragment.InterfaceCommunicator{
    static final String DB_NAME = "AccountBook";
    static final String TB_NAME = "IncomeList";
    static final String TB_NAME1 = "ExpenseList";
    static final String[] FROM = new String[]{"date", "money", "type", "payway", "member", "description"};
    static final String[] FROM1 = new String[]{"date", "money", "type", "payway", "member", "description"};
    SQLiteDatabase db;
    Button addOneBtn, accountListBtn, accountGoalBtn;
    Intent intentToAddOne, intentToAccountList, intentToAccountGoal, intentToSettings;
    Cursor cur;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        intentToAddOne = new Intent(this, AddOne.class);
        intentToAccountList = new Intent(this, AccountList.class);
        intentToAccountGoal = new Intent(this, AccountGoal.class);
        intentToSettings = new Intent(this, Settings.class);
        addOneBtn = (Button) findViewById(R.id.homePage_addOne);
        accountListBtn = (Button) findViewById(R.id.homePage_accountList);
        accountGoalBtn = (Button) findViewById(R.id.homePage_accountGoal);

    }

    private void addData(String name, String dollar) {      //新增資料庫資料
        ContentValues cv = new ContentValues();
        cv.put(FROM[0], name);
        cv.put(FROM[1], dollar);
        db.insert(TB_NAME, null, cv);
    }

    public void addone(View view) {
        startActivity(intentToAddOne);
    }

    public void accountList(View view) {
        startActivity(intentToAccountList);
    }

    public void accountGoal(View view) {
        startActivity(intentToAccountGoal);
    }

    public void settings(View view) {
        startActivity(intentToSettings);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if((event.getKeyCode()==KeyEvent.KEYCODE_BACK) && (event.getAction()==KeyEvent.ACTION_DOWN)){
            try{
                String dialogContent = getString(R.string.homePage_exitDialogText);
                String dialogLeft = getString(R.string.homePage_OKDialogText);
                String dialogRight = getString(R.string.homePage_cancelDialogText);

                ToolExitDialogFragment dialog =
                        ToolExitDialogFragment.newInstance
                                (dialogContent,dialogLeft,dialogRight);
                dialog.show(getSupportFragmentManager(), "dialog");
            }
            catch (Exception e){}
        }
        return true;
    }


    @Override
    public void sendDelValue(String value) {
        if(value.equals("Yes")){
            finishAffinity();
        }
    }

    @Override
    public void sendDelValue(String returnValue, int returnItemID) {

    }
}