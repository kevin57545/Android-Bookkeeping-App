package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AccountGoal extends AppCompatActivity {
    SQLiteDatabase db;
    Cursor cur;
    static final String DB_NAME="AccountBook";
    static final String TB_NAME="List";
    static final String[] FROM=new String[] {"moneyType","date","money","type","payway","member","description"};
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefEdit;
    EditText expenseEdt,incomeEdt;
    String hint = "";
    TextView hintTxv;
    int expenseGoal,incomeGoal,expenseMoney,incomeMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_goal);

        hintTxv = (TextView) findViewById(R.id.accountGoal_hintTxv) ;

        //引入資料庫
        db = openOrCreateDatabase(DB_NAME,  Context.MODE_PRIVATE, null);
        String createTable="CREATE TABLE IF NOT EXISTS " + TB_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "moneyType VARCHAR(16), date VARCHAR(32), " +
                "money VARCHAR(16), type VARCHAR(16), payway VARCHAR(16), member VARCHAR(16)" +
                ", description VARCHAR(16))";
        db.execSQL(createTable);
        expenseEdt = (EditText) findViewById(R.id.accountGoal_edtExpense);
        incomeEdt = (EditText) findViewById(R.id.addOne_edtMoney);

        prefs = getSharedPreferences("GOAL",MODE_PRIVATE);
        prefEdit = prefs.edit();

        if(prefs.getString("setGoal","").equals("Yes")){
            expenseGoal = Integer.parseInt(prefs.getString("expenseGoal",""));
            incomeGoal = Integer.parseInt(prefs.getString("incomeGoal",""));
            expenseEdt.setText(prefs.getString("expenseGoal",""));
            incomeEdt.setText(prefs.getString("incomeGoal",""));
            lookupTotalMoney();
        }

    }

    public void submit (View view){
        if(expenseEdt.getText().toString().equals("")||incomeEdt.getText().toString().equals("")){
            Toast.makeText(this, getString(R.string.account_goal_enterToastText),Toast.LENGTH_SHORT).show();
        }else{
            prefEdit.putString("expenseGoal",expenseEdt.getText().toString());
            prefEdit.putString("incomeGoal",incomeEdt.getText().toString());
            prefEdit.putString("setGoal","Yes");
            prefEdit.commit();
            expenseGoal = Integer.parseInt(expenseEdt.getText().toString());
            incomeGoal = Integer.parseInt(incomeEdt.getText().toString());
            hint = getString(R.string.account_goal_saveHintText) + "\n" ;
            lookupTotalMoney();
        }
    }

    public void clearGoal(View view){
        prefEdit.putString("expenseGoal","");
        prefEdit.putString("incomeGoal","");
        prefEdit.putString("setGoal","No");
        prefEdit.commit();
        hintTxv.setText("");
        expenseEdt.setText("");
        incomeEdt.setText("");
        Toast.makeText(this, getString(R.string.account_goal_clearToastText),Toast.LENGTH_SHORT).show();
    }

    public void lookupTotalMoney(){
        expenseMoney=0;
        db = openOrCreateDatabase(DB_NAME,  Context.MODE_PRIVATE, null);
        cur=db.rawQuery("SELECT * FROM "+ TB_NAME +" WHERE moneyType='Expense'", null);
        if(cur.moveToFirst()){
            do{
                expenseMoney+=Integer.parseInt(cur.getString(3))  ;
            }while(cur.moveToNext());
        }
        db.close();

        db = openOrCreateDatabase(DB_NAME,  Context.MODE_PRIVATE, null);
        incomeMoney=0;
        cur=db.rawQuery("SELECT * FROM "+ TB_NAME +" WHERE moneyType='Income'", null);
        if(cur.moveToFirst()){
            do{
                incomeMoney+=Integer.parseInt(cur.getString(3))  ;
            }while(cur.moveToNext());
        }
        db.close();

        //計算並提示總支出
        hint += getString(R.string.account_goal_expenseTotalHintText) + Integer.toString(expenseMoney) +
                getString(R.string.account_goal_dollarHintText) + "\n";
        if(expenseMoney-expenseGoal > 0) {
            hint += getString(R.string.account_goal_outExpenseHintText) +
                    Integer.toString(expenseMoney-expenseGoal) +
                    getString(R.string.account_goal_dollarHintText) + "\n";
        }else if(expenseMoney-expenseGoal == 0){
            hint += getString(R.string.account_goal_outExpenseHintText) + "\n";
        }else{
            hint += getString(R.string.account_goal_inExpenseHintText) +
                    Integer.toString(expenseGoal-expenseMoney) +
                    getString(R.string.account_goal_dollarHintText) + "\n";
        }

        //計算並提示總收入
        hint += "\n" + getString(R.string.account_goal_incomeTotalHintText) + Integer.toString(incomeMoney) +
                getString(R.string.account_goal_dollarHintText) + "\n";
        if(incomeMoney-incomeGoal > 0){
            hint += getString(R.string.account_goal_outIncomeHintText) +
                    Integer.toString(incomeMoney-incomeGoal) +
                    getString(R.string.account_goal_dollarHintText) + "\n";
        }else if(incomeMoney-incomeGoal == 0){
            hint += getString(R.string.account_goal_equalIncomeHintText) + "\n";
        }else{
            hint += getString(R.string.account_goal_inIncomeHintText) +
                    Integer.toString(incomeGoal-incomeMoney) +
                    getString(R.string.account_goal_dollarHintText) + "\n";
        }
        hintTxv.setText(hint);
    }
}