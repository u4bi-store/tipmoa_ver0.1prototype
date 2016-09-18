package com.u4bi.tipmoaproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE=1;
    private MySQLiteOpenHelper helper;
    String dbName = "goodtest.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;
    String tag = "인설트: "; // Log 에 사용할 tag
    /*-------------------------------------------*/

    Intent intent;

    Animation animation;
    ImageView imageView;
    Button[] button = new Button[11];
    TextView[] textView= new TextView[3];

    private int sendPrice=0;
    private int totalPrice=0;
    private boolean mathControll=true;

    public int getSendPrice() {
        return sendPrice;
    }
    public void setSendPrice(int sendPrice) {
        this.sendPrice = sendPrice;
    }
    public int getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    private void GiveSendTip(int price){
        if(mathControll){
            setSendPrice(getSendPrice()+price);
        }else {
            setSendPrice(getSendPrice()-price);
        }
        textView[0].setText(getSendPrice()+"원");
    }

    private void GiveTotalTip(int price){
        setTotalPrice(getTotalPrice()+price);
        textView[1].setText("누적팁: "+getTotalPrice()+"원");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        intent = getIntent();

        helper = new MySQLiteOpenHelper(this, dbName,null, dbVersion);
        try {
            db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(tag, "db연결오류");
            finish();
        }
        sumTip();
        textView[1].setText("누적팁: "+totalPrice+"원");
        /*-----------------------------------------------*/
        select();
    }

    public void sumTip(){
        Cursor cc = db.rawQuery("select sum(tipmoa_table.tip) from tipmoa_table inner join tipreg_table on tipmoa_table.tipreg=tipreg_table.tipreg where tipreg_table.tipreg='"+getInfoRegdate()+"'", null);
        if(cc.moveToNext()) {
            totalPrice = cc.getInt(0);
        }

    }


    public void select() {
        Cursor c = db.rawQuery("select tipreg from tipreg_table where tipreg='"+getInfoRegdate()+"';", null);
        if(!c.moveToNext()){
            String tipreg = getInfoRegdate();
            String sql = "insert into tipreg_table(tipreg) values('" + tipreg + "')";
            db.execSQL(sql);
        }

    }

    public void insert (int tip, String tiptime, boolean tioio) {
        int tipio;

        if(tioio){
           tipio=1;
        }else{
            tipio=0;
        }
        String tipreg=getInfoRegdate();
        String sql="insert into tipmoa_table(tip,tiptime,tipreg, tipio) values("+tip+",'"+tiptime+"','"+tipreg+"',"+tipio+");";
        db.execSQL(sql);
        Log.d(tag, "성공함 들어간 값: 팁: "+tip+"팁받은시간: "+tiptime+"팁인아웃풋: "+tipio);
    }

    public void init(){

        imageView = (ImageView) findViewById(R.id.image_math);

        textView[0] = (TextView) findViewById(R.id.text_sendprice);
        textView[1] = (TextView) findViewById(R.id.text_totalprice);
        textView[2] = (TextView) findViewById(R.id.text_math);

        button[0] = (Button) findViewById(R.id.button_price1);
        button[1] = (Button) findViewById(R.id.button_price2);
        button[2] = (Button) findViewById(R.id.button_price3);
        button[3] = (Button) findViewById(R.id.button_price4);
        button[4] = (Button) findViewById(R.id.button_price5);
        button[5] = (Button) findViewById(R.id.button_price6);

        button[6] = (Button) findViewById(R.id.button_ok);
        button[7] = (Button) findViewById(R.id.button_no);

        button[8] = (Button) findViewById(R.id.button_info);
        button[9] = (Button) findViewById(R.id.button_calendar);
        button[10] =(Button) findViewById(R.id.button_memo);

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mathControll){
                    mathControll=false;
                    textView[2].setText("-");
                }else{
                    mathControll=true;
                    textView[2].setText("+");

                }
                animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.amim_config);
                imageView.startAnimation(animation);
                turnImageLogo();
                ChangeButtonColor(mathControll);
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, SysDialog.class);
                startActivity(intent);
                return true;
            }
        });
    }

    public void turnImageLogo(){
        Matrix updownInversion = new Matrix();
        updownInversion.setScale(1, -1);

        Drawable d = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        Bitmap updownInversionImg = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), updownInversion, false);
        imageView.setImageBitmap(updownInversionImg);
    }

    public String getInfoRegdate(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy년 MM월 dd일");
        String strNow = sdfNow.format(date);
        return strNow;
    }


    public void ChangeButtonColor(boolean isbutton) {
        if (isbutton){
            for (int i = 0; i < button.length; i++) {
                if(i<6){
                    button[i].setBackgroundColor(Color.parseColor("#ff9554"));
                }else if(i>4 && i<8){
                    button[i].setBackgroundColor(Color.parseColor("#5eceab"));
                }else if(i>7){
                    button[i].setBackgroundColor(Color.parseColor("#ff9554"));
                }
            }
        }else {
            for (int i = 0; i < button.length; i++) {
                if(i<6){ // 0 1 2 3 4 5
                    button[i].setBackgroundColor(Color.parseColor("#5eceab"));
                }else if(i>4 && i<8){
                    button[i].setBackgroundColor(Color.parseColor("#ff9554"));
                }else if(i>7){
                    button[i].setBackgroundColor(Color.parseColor("#5eceab"));
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sumTip();
        textView[1].setText("누적팁: "+totalPrice+"원");

    }

    public void btnClick(View view) {
        if(view.getId()== R.id.button_price1){
            GiveSendTip(1000);

        }else if(view.getId()== R.id.button_price2){
            GiveSendTip(5000);

        }else if(view.getId()== R.id.button_price3){
            GiveSendTip(10000);

        }else if(view.getId()== R.id.button_price4){
            GiveSendTip(30000);

        }else if(view.getId()== R.id.button_price5){
            GiveSendTip(50000);

        }else if(view.getId()== R.id.button_price6){
            GiveSendTip(100000);

        }else if(view.getId()== R.id.button_ok){
            if(getSendPrice()==0){
                Toast.makeText(this,"입력하신 금액이 없습니다.",Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this,getSendPrice()+"원 정상등록 되었습니다.",Toast.LENGTH_SHORT).show();
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm");
            String strNow = sdfNow.format(date);
            boolean tipInOutPut;
            if(getSendPrice() < 0){
                tipInOutPut=false;
            }else{
                tipInOutPut=true;
            }
            insert(getSendPrice(),strNow,tipInOutPut);
            GiveTotalTip(getSendPrice());
            setSendPrice(0);
            textView[0].setText(getSendPrice()+"원");
        }else if(view.getId()== R.id.button_no){
            setSendPrice(0);
            textView[0].setText(getSendPrice()+"원");

        }else if(view.getId()== R.id.button_info){
            intent=new Intent(this, InfoActivity.class);
            intent.putExtra("tiptitle",getInfoRegdate());
            startActivityForResult(intent,REQUEST_CODE);

        }else if(view.getId()== R.id.button_calendar){
            Intent intent = new Intent(this, CalenDialog.class);
            startActivity(intent);

        }else if(view.getId()== R.id.button_memo){
            Intent intent = new Intent(this, WebDialog.class);
            startActivity(intent);
        }

    }

}


/*button_price1
button_price2
button_price3
button_price4
button_price5
button_price6

button_ok
button_no

button_info
button_calendar
button_memo
*/