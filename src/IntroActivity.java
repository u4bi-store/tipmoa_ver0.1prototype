package com.u4bi.tipmoaproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by root on 2016-05-15.
 */
public class IntroActivity extends Activity{

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_layout);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intent=new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);

    }
}
