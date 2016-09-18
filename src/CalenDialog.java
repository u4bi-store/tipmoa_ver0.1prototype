package com.u4bi.tipmoaproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by root on 2016-05-15.
 */
public class CalenDialog extends Activity {

    public static final int REQUEST_CODE=1;
    CalenAepter calenAepter;
    ListView calenListview;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.calenpopup);
        Log.d("접근","성공");
        calenAepter = new CalenAepter(this);
        calenListview=(ListView)findViewById(R.id.calen_list);
        calenListview.setAdapter(calenAepter);

        calenListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView calenitem=(TextView)view.findViewById(R.id.calen_item);
                intent=new Intent(CalenDialog.this, InfoActivity.class);
                intent.putExtra("tiptitle",calenitem.getText().toString());
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

    }
}