package com.u4bi.tipmoaproject;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by root on 2016-05-15.
 */
public class WebDialog extends Activity implements View.OnClickListener {

    private MySQLiteOpenHelper helper;
    String dbName = "goodtest.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;
    String tag = "인설트: "; // Log 에 사용할 tag
    /*-------------------------------------------*/

    EditText memo_text;
    Button memo_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.webpopup);
        memo_text = (EditText) findViewById(R.id.memo_text);
        memo_button =(Button)findViewById(R.id.memo_button);

        helper = new MySQLiteOpenHelper(this, dbName,null, dbVersion);
        try {
            db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(tag, "db연결오류");
            finish();
        }

    }

    public void insert (String tipmemo) {

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm");
        String strNow = sdfNow.format(date);
        String sql="insert into tipmemo_table(tiptime,tipmemo,tipreg) values('"+strNow+"','"+tipmemo+"','"+getInfoRegdate()+"');";
        db.execSQL(sql);
    }


    public String getInfoRegdate(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy년 MM월 dd일");
        String strNow = sdfNow.format(date);
        return strNow;
    }

    public void btnClick(View view){
        if(view.getId()== R.id.memo_button) {
                Toast.makeText(this, "메모가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                insert (memo_text.getText().toString());
                finish();
        }
    }

    public void onClick(View arg0) {

    }
}