package com.example.finalproject;

import android.app.Activity;
import android.app.Dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ToolAccountListViewDialogFragment extends DialogFragment
        implements View.OnClickListener {

    String contentText,okText,noText;
    int itemID;

    //傳入參數
    public static ToolAccountListViewDialogFragment newInstance(String contentText,String okText,String noText,int itemID) {
        ToolAccountListViewDialogFragment fragment = new ToolAccountListViewDialogFragment();
        Bundle args = new Bundle();
        args.putString("contentText", contentText);
        args.putString("okText", okText);
        args.putString("noText", noText);
        args.putInt("itemID",itemID);
        fragment.setArguments(args);
        return fragment;
    }

    //取得從外部傳入的參數，並存到變數中
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        contentText = getArguments().getString("contentText");
        okText = getArguments().getString("okText");
        noText = getArguments().getString("noText");
        itemID = getArguments().getInt("itemID");
        return super.onCreateDialog(savedInstanceState);
    }

    //設定頁面
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tool_dialog_hint, container, false);
        ((TextView) view.findViewById(R.id.dialogTitle)).setText(getString(R.string.account_list_titleDialogText));
        ((TextView) view.findViewById(R.id.dialogContent)).setText(contentText);
        ((Button) view.findViewById(R.id.dialogCheckOK)).setText(okText);
        ((Button) view.findViewById(R.id.dialogCheckOK)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.dialogCheckNO)).setText(noText);
        ((Button) view.findViewById(R.id.dialogCheckNO)).setBackgroundColor(Color.rgb(135,65,35));
        ((Button) view.findViewById(R.id.dialogCheckNO)).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.dialogCheckOK){
        } else if (view.getId()==R.id.dialogCheckNO){
            clickDelete();
        }
        dismiss();
    }

    public ToolAccountListViewDialogFragment.InterfaceCommunicator interfaceCommunicator;

    public interface InterfaceCommunicator {
        void sendDelValue(String value);
        void sendDelValue(String returnValue, int returnItemID);
    }

    private AccountList returnActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            returnActivity = (AccountList) activity;
        }
        catch (ClassCastException e) {}
    }
    //若按下刪除鍵，則回傳給呼叫者"del"
    private void clickDelete(){
        try{
            returnActivity.sendDelValue("del",itemID);
        }catch (Exception e){}
    }

}

