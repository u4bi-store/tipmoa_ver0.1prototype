package com.u4bi.tipmoaproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
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
public class InfoAepter extends BaseAdapter {


    private MySQLiteOpenHelper helper;
    String dbName = "goodtest.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;
    String tag = "SQLite";
    /*-----------------------------------------*/

    ArrayList<Tip> list= new ArrayList<Tip>();
    Context context;
    LayoutInflater layoutInflater;
    Tip tip;
    int mode=0;
    String regsql="";


    public InfoAepter(Context context) {
        this.context = context;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /*----------------*/

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
        if (mode==0){
            selectAll();
        }else if(mode==1){
            tipInSelect();
        }else if(mode==2){
            tipOutSelect();
        }
        notifyDataSetChanged();
    }

    public void ChangeDataView(){
        notifyDataSetChanged();
    }

    private void selectAll() {
        Cursor c = db.rawQuery("select tipmoa_table.id, tipmoa_table.tip,tipmoa_table.tiptime,tipmoa_table.tipreg,tipmoa_table.tipio  from tipmoa_table inner join tipreg_table on tipmoa_table.tipreg=tipreg_table.tipreg where tipreg_table.tipreg='"+regsql+"'", null);
        while(c.moveToNext()) {
            int id = c.getInt(0);
            int tips = c.getInt(1);
            String tiptime = c.getString(2);
            String tipreg = c.getString(3);
            int tipioing =c.getInt(4);

            Tip tip = new Tip();
            tip.setTip_id(id);
            tip.setTipPrice(tips);
            tip.setRegdate(tiptime);
            tip.setReg(tipreg);
            tip.setTipio(tipioing);
            list.add(tip);
        }

    }

    private void tipOutSelect() {
        Cursor c = db.rawQuery("select tipmoa_table.id, tipmoa_table.tip,tipmoa_table.tiptime,tipmoa_table.tipreg,tipmoa_table.tipio  from tipmoa_table inner join tipreg_table on tipmoa_table.tipreg=tipreg_table.tipreg where tipreg_table.tipreg='"+regsql+"' and tipmoa_table.tipio=0", null);
        while(c.moveToNext()) {
            int id = c.getInt(0);
            int tips = c.getInt(1);
            String tiptime = c.getString(2);
            String tipreg = c.getString(3);
            int tipioing =c.getInt(4);

            Tip tip = new Tip();
            tip.setTip_id(id);
            tip.setTipPrice(tips);
            tip.setRegdate(tiptime);
            tip.setReg(tipreg);
            tip.setTipio(tipioing);
            list.add(tip);
        }

    }

    private void tipInSelect() {
        Cursor c = db.rawQuery("select tipmoa_table.id, tipmoa_table.tip,tipmoa_table.tiptime,tipmoa_table.tipreg,tipmoa_table.tipio  from tipmoa_table inner join tipreg_table on tipmoa_table.tipreg=tipreg_table.tipreg where tipreg_table.tipreg='"+regsql+"' and tipmoa_table.tipio=1", null);
        while(c.moveToNext()) {
            int id = c.getInt(0);
            int tips = c.getInt(1);
            String tiptime = c.getString(2);
            String tipreg = c.getString(3);
            int tipioing =c.getInt(4);

            Tip tip = new Tip();
            tip.setTip_id(id);
            tip.setTipPrice(tips);
            tip.setRegdate(tiptime);
            tip.setReg(tipreg);
            tip.setTipio(tipioing);
            list.add(tip);
        }
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
            view = layoutInflater.inflate(R.layout.item_list,parent,false);
        }else{
            view=convertView;
        }

        RelativeLayout layout = (RelativeLayout) view;
        TextView info_regdate=(TextView)layout.findViewById(R.id.info_regdate);
        TextView info_price=(TextView)layout.findViewById(R.id.info_price);
        TextView info_tipio=(TextView)layout.findViewById(R.id.info_tipio);


        tip=list.get(position);
        info_regdate.setText(tip.getRegdate());
        info_price.setText(tip.getTipPrice()+"원");
        if(tip.getTipio() == 1){
            info_tipio.setText("수입");
            info_tipio.setTextColor(Color.parseColor("#ff9554"));
        }else{
            info_tipio.setText("지출");
            info_tipio.setTextColor(Color.parseColor("#5eceab"));
        }

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
