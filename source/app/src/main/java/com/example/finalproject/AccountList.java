package com.example.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AccountList extends AppCompatActivity implements AdapterView.OnItemClickListener,
        ToolAccountListViewDialogFragment.InterfaceCommunicator{
    SQLiteDatabase db;
    Cursor cur;
    SimpleCursorAdapter adapter;
    static final String DB_NAME="AccountBook";
    static final String TB_NAME="List";
    static final String[] FROM=new String[] {"moneyType","date","money","type","payway","member","description"};
    String moneyType,date,money,type,payway,member,description;
    int selectID,expenseMoney,incomeMoney,expenseGoal,incomeGoal;
    boolean clickExpense=false,clickIncome=false;
    ListView lv;
    Button showExpenseBtn,showIncomeBtn;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);

        showExpenseBtn = (Button) findViewById(R.id.accountList_expense);
        showIncomeBtn = (Button) findViewById(R.id.accountList_income) ;

        prefs = getSharedPreferences("GOAL",MODE_PRIVATE);
        //引入資料庫
        db = openOrCreateDatabase(DB_NAME,  Context.MODE_PRIVATE, null);
        String createTable="CREATE TABLE IF NOT EXISTS " + TB_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "moneyType VARCHAR(16), date VARCHAR(32), " +
                "money VARCHAR(16), type VARCHAR(16), payway VARCHAR(16), member VARCHAR(16)" +
                ", description VARCHAR(16))";
        db.execSQL(createTable);
        lv = (ListView) findViewById(R.id.accountList_ListView);
        showAccountList();

        if(prefs.getString("setGoal","").equals("Yes")){
            expenseGoal = Integer.parseInt(prefs.getString("expenseGoal",""));
            incomeGoal = Integer.parseInt(prefs.getString("incomeGoal",""));
            if(expenseMoney >= expenseGoal){
                Toast.makeText(this, getString(R.string.account_list_outExpenseGoalToastText),Toast.LENGTH_SHORT).show();
            }else if(expenseGoal - expenseMoney <= 200){
                Toast.makeText(this, getString(R.string.account_list_nearExpenseGoalToastText) +
                        Integer.toString(expenseGoal - expenseMoney) +
                        getString(R.string.account_goal_dollarHintText),Toast.LENGTH_SHORT).show();
            }
            if(incomeMoney >= incomeGoal){
                Toast.makeText(this, getString(R.string.account_list_outIncomeGoalToastText),Toast.LENGTH_SHORT).show();
            }else if(incomeGoal - incomeMoney <= 200){
                Toast.makeText(this, getString(R.string.account_list_nearIncomeGoalToastText) +
                        Integer.toString(incomeGoal - incomeMoney) +
                        getString(R.string.account_goal_dollarHintText),Toast.LENGTH_SHORT).show();
            }
        }
    }

    //計算資料庫支出總金額並顯示在按鈕上
    public void calExpenseTotal(){
        expenseMoney=0;
        db = openOrCreateDatabase(DB_NAME,  Context.MODE_PRIVATE, null);
        cur=db.rawQuery("SELECT * FROM "+ TB_NAME +" WHERE moneyType='Expense'", null);
        if(cur.moveToFirst()){
            do{
                expenseMoney+=Integer.parseInt(cur.getString(3))  ;
            }while(cur.moveToNext());
        }
        db.close();
        showExpenseBtn.setText(getString(R.string.addOne_expenseText)+":"+Integer.toString(expenseMoney) + getString(R.string.account_goal_dollarHintText));
    }

    //計算資料庫收入總金額並顯示在按鈕上
    public void calIncomeTotal(){
        incomeMoney=0;
        db = openOrCreateDatabase(DB_NAME,  Context.MODE_PRIVATE, null);
        cur=db.rawQuery("SELECT * FROM "+ TB_NAME +" WHERE moneyType='Income'", null);
        if(cur.moveToFirst()){
            do{
                incomeMoney+=Integer.parseInt(cur.getString(3))  ;
            }while(cur.moveToNext());
        }
        db.close();
        showIncomeBtn.setText(getString(R.string.addOne_incomeText) + ":" + Integer.toString(incomeMoney) + getString(R.string.account_goal_dollarHintText));
    }


    public void showExpense(View view){
        if(!clickExpense){
            showExpenseBtn.setTextColor(Color.rgb(238,238,228));
            showExpenseBtn.setBackgroundColor(Color.rgb(21,76,121));
            showIncomeBtn.setTextColor(Color.rgb(6,57,112));
            showIncomeBtn.setBackgroundColor(Color.rgb(255,255,255));
            clickExpense = true;
            clickIncome = false;
            showChosenList(0);
        }else{
            showExpenseBtn.setTextColor(Color.rgb(6,57,112));
            showExpenseBtn.setBackgroundColor(Color.rgb(255,255,255));
            clickExpense = false;
            showAccountList();
        }
    }

    public void showIncome(View view){
        if(!clickIncome){
            showIncomeBtn.setTextColor(Color.rgb(238,238,228));
            showIncomeBtn.setBackgroundColor(Color.rgb(21,76,121));
            showExpenseBtn.setTextColor(Color.rgb(6,57,112));
            showExpenseBtn.setBackgroundColor(Color.rgb(255,255,255));
            clickIncome = true;
            clickExpense = false;
            showChosenList(1);
        }else{
            showIncomeBtn.setTextColor(Color.rgb(6,57,112));
            showIncomeBtn.setBackgroundColor(Color.rgb(255,255,255));
            clickIncome = false;
            showAccountList();
        }
    }

    //顯示所有收支紀錄
    public void showAccountList(){
        try {
            db = openOrCreateDatabase(DB_NAME,  Context.MODE_PRIVATE, null);
            cur = db.rawQuery("SELECT * FROM "+TB_NAME,null);
            if(cur.getCount()==0){
                lv.setVisibility(View.INVISIBLE);
            } else {
                lv.setVisibility(View.VISIBLE);
                adapter = new SimpleCursorAdapter(this,
                        R.layout.tool_listview_account_list,
                        cur,
                        FROM,
                        new int[] {R.id.accountListLv_moneyType,R.id.accountListLv_date,
                                R.id.accountListLv_money,R.id.accountListLv_type,
                                R.id.accountListLv_payway,R.id.accountListLv_member,
                                R.id.accountListLv_description
                        },
                        0);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(this);
            }
            db.close();
            calExpenseTotal();//計算總支出金額
            calIncomeTotal();//計算總收入金額
        } catch (Exception e){
            //DevToolDebug.catchException(e);
        }
    }

    //顯示所選的支出或收入紀錄
    public void showChosenList(int i){
        try {
            db = openOrCreateDatabase(DB_NAME,  Context.MODE_PRIVATE, null);
            if(i==0)
                cur=db.rawQuery("SELECT * FROM "+ TB_NAME +" WHERE moneyType='Expense'", null);
            if(i==1)
                cur=db.rawQuery("SELECT * FROM "+ TB_NAME +" WHERE moneyType='Income'", null);
            if(cur.getCount()==0){

                lv.setVisibility(View.INVISIBLE);
            } else {
                lv.setVisibility(View.VISIBLE);

                adapter = new SimpleCursorAdapter(this,
                        R.layout.tool_listview_account_list,
                        cur,
                        FROM,
                        new int[] {R.id.accountListLv_moneyType,R.id.accountListLv_date,
                                R.id.accountListLv_money,R.id.accountListLv_type,
                                R.id.accountListLv_payway,R.id.accountListLv_member,
                                R.id.accountListLv_description
                        },
                        0);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(this);
            }
            db.close();
        } catch (Exception e){ //DevToolDebug.catchException(e);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(!clickExpense && !clickIncome){
            db = openOrCreateDatabase(DB_NAME,  Context.MODE_PRIVATE, null);
            cur = db.rawQuery("SELECT * FROM "+TB_NAME,null);
        }
        cur.moveToPosition(i);
        try {
            selectID = cur.getInt(0);
            System.out.println("selectID" + selectID);
            moneyType = cur.getString(1);
            date = cur.getString(2);
            money = cur.getString(3);
            type = cur.getString(4);
            payway = cur.getString(5);
            member = cur.getString(6);
            description = cur.getString(7);
            //startManagingCursor(cur);
            showDetail();
        }catch (Exception e){}
    }

    private void showDetail(){
        try{
        String dialogContent = getString(R.string.account_list_detailDialogText) + "\n\n"
                + getString(R.string.account_list_typeText) + " : " + moneyType + "\n"
                + getString(R.string.addOne_dateText) + " : " + date + "\n"
                + getString(R.string.addOne_moneyText) + " : " + money + getString(R.string.account_goal_dollarHintText) + "\n"
                + getString(R.string.addOne_typeText) + " : " + type + "\n"
                + getString(R.string.account_list_wayText) + " : " + payway + "\n"
                + getString(R.string.addOne_memberText) + " : " + member + "\n"
                + getString(R.string.addOne_descriptionText) + " : " + description ;
        String dialogLeft = getString(R.string.account_list_OKDialogText);
        String dialogRight = getString(R.string.account_list_deleteDialogText);

            ToolAccountListViewDialogFragment dialog =
                    ToolAccountListViewDialogFragment.newInstance
                            (dialogContent,dialogLeft,dialogRight,selectID);
            dialog.show(getSupportFragmentManager(), "dialog");
        }
        catch (Exception e){}
    }

    //---------------------用戶action------------------------


    @Override
    public void sendDelValue(String value) {

    }

    //DialogFragmemt回傳刪除後執行動作
    @Override
    public void sendDelValue(String returnValue, int returnItemID) {
        if(returnValue.equals("del")){
            db = openOrCreateDatabase(DB_NAME,  Context.MODE_PRIVATE, null);
            db.delete(TB_NAME,"_id="+returnItemID,null);
            db.close();
            if(clickExpense) {
                showChosenList(0);
                calExpenseTotal();
            }
            else if(clickIncome) {
                showChosenList(1);
                calIncomeTotal();
            }
            else showAccountList();
        }
    }

}
