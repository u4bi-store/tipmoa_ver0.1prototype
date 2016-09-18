package com.u4bi.tipmoaproject;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by root on 2016-05-15.
 */
public class SysDialog extends Activity implements View.OnClickListener {

    Button sys_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.syspopup);
        sys_button =(Button)findViewById(R.id.sys_button);

    }

    public void btnClick(View view){
        if(view.getId()== R.id.sys_button) {
                Toast.makeText(this, "설정이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
        }
    }

    public void onClick(View arg0) {

    }
}