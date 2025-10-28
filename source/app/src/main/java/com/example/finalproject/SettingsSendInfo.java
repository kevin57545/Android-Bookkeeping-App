package com.example.finalproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;

public class SettingsSendInfo extends AppCompatActivity
        implements View.OnClickListener {

    View pageBackground;
    TextInputEditText findDevInput;
    Button addImageButton;
    ImageView imagePreview;
    TextInputEditText descriptionEdt;
    TextView previewTxv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_send_info);

        pageBackground = (View) findViewById(R.id.setting_help_findDevPage);
        pageBackground.setOnClickListener(this);

        findDevInput = (TextInputEditText) findViewById(R.id.settings_sendInfo_description);
        addImageButton = (Button) findViewById(R.id.settings_sendInfo_sendPhotoBtn);
        addImageButton.setOnClickListener(this);
        previewTxv = (TextView) findViewById(R.id.settings_sendInfo_previewTxv);
        imagePreview = (ImageView) findViewById(R.id.settings_sendInfo_photoPreview);
        ((Button) findViewById(R.id.settings_sendInfo_submitBtn)).setOnClickListener(this);
        descriptionEdt = (TextInputEditText) findViewById(R.id.settings_sendInfo_description);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.settings_sendInfo_submitBtn){
            try {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"s110916013@stu.ntue.edu.tw"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.setting_sendInfo_sendText));
                emailIntent.putExtra(Intent.EXTRA_TEXT   , descriptionEdt.getText().toString());
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    Toast.makeText(this, getString(R.string.setting_sendInfo_sendToastText),Toast.LENGTH_SHORT).show();
                } catch (android.content.ActivityNotFoundException e) {
                    Toast.makeText(this, getString(R.string.settings_aboutAPP_Contact_emailToastErrorText),Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e){
            }
        } else if(view.getId()==R.id.settings_sendInfo_sendPhotoBtn){
            getImage();
        }
    }

    //取得圖片
    public void getImage(){
        Intent intent = new Intent();
        intent.setType("image/*");//設定類型
        intent.setAction(Intent.ACTION_GET_CONTENT);//開啟選取圖檔視窗
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {//user按下確定後
            //使用Uri取得圖檔的路徑
            Uri uri = data.getData();
            //抽象資料的接口
            ContentResolver cr = this.getContentResolver();
            try {
                //由抽象資料接口轉換圖檔路徑為Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                //將Bitmap設定到ImageView
                imagePreview.setImageBitmap(bitmap);
                previewTxv.setText(getString(R.string.settings_sendInfo_previewPhotoText));
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
