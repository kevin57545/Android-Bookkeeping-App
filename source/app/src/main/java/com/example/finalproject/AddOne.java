package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AddOne extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener{
    static final String DB_NAME="AccountBook";
    static final String TB_NAME="List";
    int year, month, day;
    String date;
    RadioGroup typeGroupRadio;
    Button dateBtn,paywayBtn,incomeWayBtn,expenseTypeBtn,incomeTypeBtn,memberBtn;
    TextInputEditText descriptionEdt;
    static final String[] FROM=new String[] {"moneyType","date","money","type","payway","member","description"};
    EditText moneyEdt;
    SQLiteDatabase db;
    Cursor cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_one);

        typeGroupRadio = (RadioGroup) findViewById(R.id.addOne_typeGroupRadio);
        typeGroupRadio.setOnCheckedChangeListener(this);

        moneyEdt = (EditText) findViewById(R.id.addOne_edtMoney);
        descriptionEdt = (TextInputEditText) findViewById(R.id.addOne_description);

        //將日期初始化為今天
        dateBtn = (Button) findViewById(R.id.addOne_date);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        date = year + "/" + (month + 1) + "/" + day;
        dateBtn.setText(date);

        paywayBtn = (Button) findViewById(R.id.addOne_expenseWay);
        incomeWayBtn = (Button) findViewById(R.id.addOne_incomeWay);
        expenseTypeBtn = (Button) findViewById(R.id.addOne_expenseType);
        incomeTypeBtn = (Button) findViewById(R.id.addOne_incomeType);
        memberBtn = (Button) findViewById(R.id.addOne_member);
        ((LinearLayout) findViewById(R.id.addOne_incomeWay_Layout)).setVisibility(View.GONE);
        ((LinearLayout) findViewById(R.id.addOne_incomeType_Layout)).setVisibility(View.GONE);

        //新增資料庫
        db = openOrCreateDatabase(DB_NAME,  Context.MODE_PRIVATE, null);//新增資料庫
        String createTable="CREATE TABLE IF NOT EXISTS " + TB_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "moneyType VARCHAR(16), date VARCHAR(32), " +
                "money VARCHAR(16), type VARCHAR(16), payway VARCHAR(16), member VARCHAR(16)" +
                ", description VARCHAR(16))";
        db.execSQL(createTable);
        cur=db.rawQuery("SELECT * FROM "+ TB_NAME, null);

    }

    public void save(View view){
        if(moneyEdt.getText().toString().equals("")){
            Toast.makeText(this, getString(R.string.addOne_moneyToastText),Toast.LENGTH_SHORT).show();
        }else{
            if (typeGroupRadio.getCheckedRadioButtonId() == R.id.addOne_expenseRadioBtn) {
                saveExpenseToSQL();
                finish();
            } else if (typeGroupRadio.getCheckedRadioButtonId() == R.id.addOne_incomeRadioBtn) {
                saveIncomeToSQL();
                finish();
            }
        }
    }

    public void saveExpenseToSQL(){
        String money = moneyEdt.getText().toString();
        String payway = paywayBtn.getText().toString();
        String expenseType = expenseTypeBtn.getText().toString();
        String member = memberBtn.getText().toString();
        String description = descriptionEdt.getText().toString();
        try{
            ContentValues cv=new ContentValues();
            cv.put(FROM[0], "Expense");
            cv.put(FROM[1], date);
            cv.put(FROM[2], money);
            cv.put(FROM[3], expenseType);
            cv.put(FROM[4], payway);
            cv.put(FROM[5], member);
            cv.put(FROM[6], description);
            db.insert(TB_NAME, null, cv);
            Toast.makeText(this,getString(R.string.addOne_successToastText),Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this, getString(R.string.addOne_errorToastText),Toast.LENGTH_SHORT).show();
        }
    }

    public void saveIncomeToSQL(){
        String money = moneyEdt.getText().toString();
        String payway = paywayBtn.getText().toString();
        String incomeType = incomeTypeBtn.getText().toString();
        String member = memberBtn.getText().toString();
        String description = descriptionEdt.getText().toString();
        try{
            ContentValues cv=new ContentValues();
            cv.put(FROM[0], "Income");
            cv.put(FROM[1], date);
            cv.put(FROM[2], money);
            cv.put(FROM[3], incomeType);
            cv.put(FROM[4], payway);
            cv.put(FROM[5], member);
            cv.put(FROM[6], description);
            db.insert(TB_NAME, null, cv);
            Toast.makeText(this, getString(R.string.addOne_successToastText),Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this, getString(R.string.addOne_errorToastText),Toast.LENGTH_SHORT).show();
        }
    }

    public void payway(View view){
        PopupMenu popup = new PopupMenu(this,paywayBtn);
        popup.getMenuInflater().inflate(R.menu.payway_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.expense_money:
                        paywayBtn.setText(getString(R.string.paywayText_cash));
                        break;
                    case R.id.expense_bank:
                        paywayBtn.setText(getString(R.string.paywayText_bank));
                        break;
                    case R.id.expense_creditCard:
                        paywayBtn.setText(getString(R.string.paywayText_creditCard));
                        break;
                    case R.id.expense_debitCard:
                        paywayBtn.setText(getString(R.string.paywayText_debitCard));
                        break;
                    case R.id.expense_others:
                        paywayBtn.setText(getString(R.string.paywayText_other));
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    public void incomeWay(View view){
        PopupMenu popup = new PopupMenu(this,incomeWayBtn);
        popup.getMenuInflater().inflate(R.menu.payway_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.expense_money:
                        incomeWayBtn.setText(getString(R.string.paywayText_cash));
                        break;
                    case R.id.expense_bank:
                        incomeWayBtn.setText(getString(R.string.paywayText_bank));
                        break;
                    case R.id.expense_creditCard:
                        incomeWayBtn.setText(getString(R.string.paywayText_creditCard));
                        break;
                    case R.id.expense_debitCard:
                        incomeWayBtn.setText(getString(R.string.paywayText_debitCard));
                        break;
                    case R.id.expense_others:
                        incomeWayBtn.setText(getString(R.string.paywayText_other));
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    public void dateChoose(View view){
        Calendar calendar = Calendar.getInstance();
        int chooseYear = calendar.get(Calendar.YEAR);//取得現在的日期年月日
        int chooseMonth = calendar.get(Calendar.MONTH);
        int chooseDay = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(view.getContext(), R.style.ThemeOverlay_App_DatePicker,
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int chooseYear, int chooseMonth, int chooseDay) {
                date = chooseYear + "/" + (chooseMonth + 1) + "/" + chooseDay;
                dateBtn.setText(date);
            }
        }, chooseYear, chooseMonth, chooseDay).show();
    }


    public void expenseType(View view){
        PopupMenu popup = new PopupMenu(this,expenseTypeBtn);
        popup.getMenuInflater().inflate(R.menu.expensetype_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.expenseType_food:
                        expenseTypeBtn.setText(getString(R.string.expenseTypeText_food));
                        break;
                    case R.id.expenseType_transportation:
                        expenseTypeBtn.setText(getString(R.string.expenseTypeText_transportation));
                        break;
                    case R.id.expenseType_entertainment:
                        expenseTypeBtn.setText(getString(R.string.expenseTypeText_entertainment));
                        break;
                    case R.id.expenseType_shopping:
                        expenseTypeBtn.setText(getString(R.string.expenseTypeText_shopping));
                        break;
                    case R.id.expenseType_garment:
                        expenseTypeBtn.setText(getString(R.string.expenseTypeText_garment));
                        break;
                    case R.id.expenseType_bill:
                        expenseTypeBtn.setText(getString(R.string.expenseTypeText_bill));
                        break;
                    case R.id.expenseType_health:
                        expenseTypeBtn.setText(getString(R.string.expenseTypeText_health));
                        break;
                    case R.id.expenseType_sporting:
                        expenseTypeBtn.setText(getString(R.string.expenseTypeText_sporting));
                        break;
                    case R.id.expenseType_education:
                        expenseTypeBtn.setText(getString(R.string.expenseTypeText_education));
                        break;
                    case R.id.expenseType_office:
                        expenseTypeBtn.setText(getString(R.string.expenseTypeText_office));
                        break;
                    case R.id.expenseType_other:
                        expenseTypeBtn.setText(getString(R.string.expenseTypeText_other));
                        break;
                }
                return true;
            }
        });
        popup.show();
    }


    public void incomeType (View view){
        PopupMenu popup = new PopupMenu(this,incomeTypeBtn);
        popup.getMenuInflater().inflate(R.menu.incometype_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.incomeType_salary:
                        incomeTypeBtn.setText(getString(R.string.incomeTypeText_salary));
                        break;
                    case R.id.incomeType_pocketMoney:
                        incomeTypeBtn.setText(getString(R.string.incomeTypeText_pocketMoney));
                        break;
                    case R.id.incomeType_bonus:
                        incomeTypeBtn.setText(getString(R.string.incomeTypeText_bonus));
                        break;
                    case R.id.incomeType_donation:
                        incomeTypeBtn.setText(getString(R.string.incomeTypeText_donation));
                        break;
                    case R.id.incomeType_rent:
                        incomeTypeBtn.setText(getString(R.string.incomeTypeText_rent));
                        break;
                    case R.id.incomeType_refund:
                        incomeTypeBtn.setText(getString(R.string.incomeTypeText_refund));
                        break;
                    case R.id.expenseType_dividend:
                        incomeTypeBtn.setText(getString(R.string.incomeTypeText_dividend));
                        break;
                    case R.id.expenseType_other:
                        incomeTypeBtn.setText(getString(R.string.incomeTypeText_other));
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    public void memberChoose (View view){
        PopupMenu popup = new PopupMenu(this,memberBtn);
        popup.getMenuInflater().inflate(R.menu.member_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.member_myself:
                        memberBtn.setText(getString(R.string.memberText_myself));
                        break;
                    case R.id.member_family:
                        memberBtn.setText(getString(R.string.memberText_family));
                        break;
                    case R.id.member_relative:
                        memberBtn.setText(getString(R.string.memberText_relative));
                        break;
                    case R.id.member_children:
                        memberBtn.setText(getString(R.string.memberText_children));
                        break;
                    case R.id.member_parents:
                        memberBtn.setText(getString(R.string.memberText_parents));
                        break;
                    case R.id.member_husband:
                        memberBtn.setText(getString(R.string.memberText_husband));
                        break;
                    case R.id.member_wife:
                        memberBtn.setText(getString(R.string.memberText_wife));
                        break;
                    case R.id.member_company:
                        memberBtn.setText(getString(R.string.memberText_company));
                        break;
                    case R.id.member_school:
                        memberBtn.setText(getString(R.string.memberText_school));
                        break;
                    case R.id.member_publicAgency:
                        memberBtn.setText(getString(R.string.memberText_publicAgency));
                        break;
                    case R.id.member_other:
                        memberBtn.setText(getString(R.string.memberText_other));
                        break;
                }
                return true;
            }
        });
        popup.show();
    }
    //類型更改時重新調整版面
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (typeGroupRadio.getCheckedRadioButtonId() == R.id.addOne_expenseRadioBtn) {
                ((LinearLayout) findViewById(R.id.addOne_payway_Layout)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.addOne_expenseType_Layout)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.addOne_incomeWay_Layout)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.addOne_incomeType_Layout)).setVisibility(View.GONE);
            } else if (typeGroupRadio.getCheckedRadioButtonId() == R.id.addOne_incomeRadioBtn) {
                ((LinearLayout) findViewById(R.id.addOne_incomeWay_Layout)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.addOne_incomeType_Layout)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.addOne_payway_Layout)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.addOne_expenseType_Layout)).setVisibility(View.GONE);
            }
        }

}