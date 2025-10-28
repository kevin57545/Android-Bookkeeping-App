package com.example.finalproject;

import android.app.Activity;
import android.app.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ToolExitDialogFragment extends DialogFragment
        implements View.OnClickListener {

    String contentText,okText,noText;

    //傳入參數
    public static ToolExitDialogFragment newInstance(String contentText,String okText,String noText) {
        ToolExitDialogFragment fragment = new ToolExitDialogFragment();
        Bundle args = new Bundle();
        args.putString("contentText", contentText);
        args.putString("okText", okText);
        args.putString("noText", noText);
        fragment.setArguments(args);
        return fragment;
    }

    //取得從外部傳入的參數，並存到變數中
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        contentText = getArguments().getString("contentText");
        okText = getArguments().getString("okText");
        noText = getArguments().getString("noText");

        return super.onCreateDialog(savedInstanceState);
    }

    //設定頁面
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tool_dialog_hint, container, false);
        ((TextView) view.findViewById(R.id.dialogContent)).setText(contentText);
        ((Button) view.findViewById(R.id.dialogCheckOK)).setText(okText);
        ((Button) view.findViewById(R.id.dialogCheckOK)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.dialogCheckNO)).setText(noText);
        ((Button) view.findViewById(R.id.dialogCheckNO)).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.dialogCheckOK){
            clickConfirm();
        } else if (view.getId()==R.id.dialogCheckNO){
        }
        dismiss();
    }

    public ToolAccountListViewDialogFragment.InterfaceCommunicator interfaceCommunicator;

    public interface InterfaceCommunicator {
        void sendDelValue(String value);
        void sendDelValue(String returnValue, int returnItemID);
    }

    private HomePage returnActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            returnActivity = (HomePage) activity;
        }
        catch (ClassCastException e) {}
    }

    private void clickConfirm(){
        try{
            returnActivity.sendDelValue("Yes");
        }catch (Exception e){}
    }

}

