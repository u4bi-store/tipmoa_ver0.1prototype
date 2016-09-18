package com.u4bi.tipmoaproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by root on 2016-05-14.
 */
public class CalenAepter extends BaseAdapter {

    Intent intent;
    private MySQLiteOpenHelper helper;
    String dbName = "goodtest.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;
    String tag = "SQLite";
    /*-----------------------------------------*/

    ArrayList<Reg> list= new ArrayList<Reg>();
    Context context;
    LayoutInflater layoutInflater;

    Button calen_iteminfo;

    Reg reg;

    public CalenAepter(Context context) {

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
        selectAll();


    }

    private void selectAll() {
        Cursor c = db.rawQuery("SELECT * FROM tipreg_table;", null);
        while(c.moveToNext()) {
            int id = c.getInt(0);
            String tipreg = c.getString(1);
            Reg reg = new Reg();
            reg.setReg_id(id);
            reg.setReg(tipreg);
            list.add(reg);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=null;

        if(convertView==null){
            view = layoutInflater.inflate(R.layout.item3_list,parent,false);
        }else{
            view=convertView;
        }

        LinearLayout layout = (LinearLayout) view;
        TextView calen_item=(TextView)layout.findViewById(R.id.calen_item);
        reg= list.get(position);
        calen_item.setText(reg.getReg().toString());

        return view;
    }


}
