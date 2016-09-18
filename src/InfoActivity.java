package com.u4bi.tipmoaproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 2016-05-14.
 */
public class InfoActivity extends Activity{

    private MySQLiteOpenHelper helper;
    String dbName = "goodtest.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;
    String tag = "인설트: "; // Log 에 사용할 tag
    /*-------------------------------------------*/

    Button[] button= new Button[2];
    ImageView info_logo;
    TextView info_total;
    TextView info_io;
    TextView info_calen;
    ListView[] listView = new ListView[2];
    InfoAepter infoAepter;
    MemoAepter memoAepter;


    TextView infoClick_price;
    TextView infoClick_regedate;
    TextView infoClick_tipio;
    TextView infoClick_memo;
    Intent intent;

    String tiptitle;
    Animation animation;
    private int tipModeio=0;
    int tipsum;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);
        info_logo=(ImageView)findViewById(R.id.info_logo);
        info_io=(TextView)findViewById(R.id.info_io);
        info_total =(TextView)findViewById(R.id.info_total);
        info_calen =(TextView)findViewById(R.id.info_calen);
        listView[0]=(ListView)findViewById(R.id.tipinfo);
        listView[1]=(ListView)findViewById(R.id.memoinfo);
        button[0]=(Button)findViewById(R.id.info_in);
        button[1]=(Button)findViewById(R.id.info_out);


        infoAepter=new InfoAepter(this);
        memoAepter=new MemoAepter(this);

        intent = getIntent();
        tiptitle =intent.getStringExtra("tiptitle");
        info_calen.setText(tiptitle+" 보고서");
        infoAepter.regsql=tiptitle;
        memoAepter.regsql=tiptitle;
        infoAepter.SelectInfo();
        memoAepter.SelectInfo();


        listView[1].setAdapter(memoAepter);
        listView[0].setAdapter(infoAepter);

        listView[0].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                infoClick_price=(TextView)view.findViewById(R.id.info_price);
                infoClick_regedate=(TextView)view.findViewById(R.id.info_regdate);
                infoClick_tipio=(TextView)view.findViewById(R.id.info_tipio);
                Log.d("돈내역",infoClick_price.getText().toString());
                Log.d("어텝터 모드",infoAepter.mode+"번");

            }
        });

        listView[1].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                infoClick_memo=(TextView)view.findViewById(R.id.info_memo);
                Log.d("메모내역",infoClick_memo.getText().toString());

            }
        });

        info_logo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                infoAepter.mode=0;
                infoAepter.regsql=tiptitle;
                Log.d("어텝터 모드",infoAepter.mode+"번");
                infoAepter.SelectInfo();
                info_io.setText("");
                animation = AnimationUtils.loadAnimation(InfoActivity.this,R.anim.amim_config);
                animation.setDuration(100);
                info_logo.startAnimation(animation);
                Toast.makeText(InfoActivity.this,"전체 내역을 봅니다.",Toast.LENGTH_SHORT).show();
            }
        });


        info_logo.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                db.execSQL("delete from tipmoa_table where tipreg='"+tiptitle+"'");
                Toast.makeText(InfoActivity.this,"모든 기록이 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                infoAepter.mode=0;
                info_io.setText("");
                infoAepter.SelectInfo();
                info_total.setText("누적팁: 0원");
                db.execSQL("delete from tipmemo_table where tipreg='"+tiptitle+"'");
                memoAepter.SelectInfo();
                return true;
            }
        });

        helper = new MySQLiteOpenHelper(this, dbName,null, dbVersion);
        try {
            db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(tag, "db연결오류");
            finish();
        }
        button[0].setBackgroundColor(Color.parseColor("#ff9554"));
        button[1].setBackgroundColor(Color.parseColor("#5eceab"));
        sumTip();
    }



    public void sumInTip(){
        Cursor cc = db.rawQuery("select sum(tipmoa_table.tip) from tipmoa_table inner join tipreg_table on tipmoa_table.tipreg=tipreg_table.tipreg where tipreg_table.tipreg='"+tiptitle+"' and tipmoa_table.tipio=1", null);
        if(cc.moveToNext()) {
            info_io.setText("수입: "+cc.getInt(0)+"원");
            info_io.setTextColor(Color.parseColor("#ff9554"));
        }

    }

    public void sumTip(){
        Cursor cc = db.rawQuery("select sum(tipmoa_table.tip) from tipmoa_table inner join tipreg_table on tipmoa_table.tipreg=tipreg_table.tipreg where tipreg_table.tipreg='"+tiptitle+"'", null);
        if(cc.moveToNext()) {
            tipsum = cc.getInt(0);
            info_total.setText("누적팁: "+tipsum+"원");
        }

    }

    public void sumOutTip(){
        Cursor cc = db.rawQuery("select sum(tipmoa_table.tip) from tipmoa_table inner join tipreg_table on tipmoa_table.tipreg=tipreg_table.tipreg where tipreg_table.tipreg='"+tiptitle+"' and tipmoa_table.tipio=0", null);
        if(cc.moveToNext()) {
            info_io.setText("지출: "+cc.getInt(0)+"원");
            info_io.setTextColor(Color.parseColor("#5eceab"));
        }

    }


    public void btnClick(View view){
        if(view.getId()== R.id.info_in) {
            infoAepter.mode=1;
            infoAepter.regsql=tiptitle;
            Log.d("어텝터 모드",infoAepter.mode+"번");
            infoAepter.SelectInfo();
            sumInTip();

        }else if(view.getId()== R.id.info_out) {
            infoAepter.mode=2;
            infoAepter.regsql=tiptitle;
            Log.d("어텝터 모드",infoAepter.mode+"번");
            infoAepter.SelectInfo();
            sumOutTip();
        }
    }
}
