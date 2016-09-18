package com.u4bi.tipmoaproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 2016-05-14.
 */
public class MemoAepter extends BaseAdapter {

    private MySQLiteOpenHelper helper;
    String dbName = "goodtest.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;
    String tag = "SQLite";
    /*-----------------------------------------*/

    ArrayList<Memo> list= new ArrayList<Memo>();
    Context context;
    LayoutInflater layoutInflater;
    Memo memo;
    String regsql="";

    public MemoAepter(Context context) {
        this.context = context;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        helper = new MySQLiteOpenHelper(context, dbName,null, dbVersion);
        try {
            db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(tag, "db연결오류");

        }

    }

    public void SelectInfo(){

        list.clear();
        selectAll();
        notifyDataSetChanged();
        Log.d("dd","sf");
    }

    private void selectAll() {
        Cursor c = db.rawQuery("select tipmemo_table.id,tipmemo_table.tiptime,tipmemo_table.tipmemo,tipmemo_table.tipreg  from tipmemo_table inner join tipreg_table on tipmemo_table.tipreg=tipreg_table.tipreg where tipreg_table.tipreg='"+regsql+"'", null);
        while(c.moveToNext()) {
            int memoid = c.getInt(0);
            String tipregdate = c.getString(1);
            String tipmemo = c.getString(2);
            String tipreg = c.getString(3);

            Memo memo = new Memo();
            memo.setMemo_id(memoid);
            memo.setRegdate(tipregdate);
            memo.setMemo(tipmemo);
            memo.setReg(tipreg);
            list.add(memo);

        }

    }

    public void ChangeDataView(){
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;

        if(convertView==null){
            view = layoutInflater.inflate(R.layout.item2_list,parent,false);
        }else{
            view=convertView;
        }

        LinearLayout layout = (LinearLayout) view;
        TextView info_regd=(TextView)layout.findViewById(R.id.info_regd);
        TextView info_memo=(TextView)layout.findViewById(R.id.info_memo);

        memo=list.get(position);
        info_regd.setText(memo.getRegdate());
        info_memo.setText(memo.getMemo());

        return view;
    }

    public String getInfoRegdate(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy년 MM월 dd일");
        String strNow = sdfNow.format(date);
        return strNow;
    }
}
