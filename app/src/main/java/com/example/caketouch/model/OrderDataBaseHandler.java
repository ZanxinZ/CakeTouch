package com.example.caketouch.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.caketouch.MainActivity;
import com.example.caketouch.table.Table;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class OrderDataBaseHandler extends SQLiteOpenHelper {
    Context context;
    private static String DATABASE_NAME = "order.db";
    private static int DATABASE_VERSION = 1;
    private static String createTableQuery = "create table ordered (tableNo TEXT, tableObj BLOB)";
    public OrderDataBaseHandler(@Nullable Context context){
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        this.context =context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(createTableQuery);
            Toast.makeText(context, "SQLite create Ordered.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context, "SQLite create fail." + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void storeTable(Table table, int tableNo){
        if (table == null){
            return;
        }

        try{
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            byte[] tableInByte = tableToBytes(table);

            ContentValues contentValues = new ContentValues();
            contentValues.put("tableNo", String.valueOf(tableNo));
            contentValues.put("tableObj", tableInByte);

            long checkIfQueryRun = sqLiteDatabase.insert("ordered",null, contentValues);
            if (checkIfQueryRun != 0){
                //Toast.makeText(context, "订单已保存！", Toast.LENGTH_SHORT).show();
                Log.d("数据库", "订单已保存！");
            }
            else{
                Log.d("数据库", "订单保存失败。");
                Toast.makeText(context, "订单保存失败。", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context, "fail:" + e, Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteTable(int tableNo){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int res = sqLiteDatabase.delete("ordered", "tableNo=?", new String[]{String.valueOf(tableNo)});
        Log.d("删除table:", String.valueOf(res));

    }

    public boolean isExist(int tableNo){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("ordered", new String[]{"tableNo"}, "tableNo=?", new String[]{String.valueOf(tableNo)},null,null, null);
        if (cursor.getCount() != 0)
        {
            Log.d("查询Ordered:", String.valueOf(cursor.getCount()));
            return true;
        }
        return false;
    }

    public Table findTableByTableNo(int tableNo){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("ordered", new String[]{"tableObj"}, "tableNo=?", new String[]{String.valueOf(tableNo)},null,null, null);
        if (cursor.moveToNext()){
            Table table = byteToTable(cursor.getBlob(cursor.getColumnIndexOrThrow("tableObj")));
            cursor.close();
            return table;
        }
        cursor.close();
        return null;
    }

    public boolean updateTable(Table table,int tableNo){
        if (isExist(tableNo)){
            deleteTable(tableNo);
        }
        storeTable(table, tableNo);
        return  true;
    }

    public ArrayList<Table> loadAllTable(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("ordered", null, null, null,null,null,null);
        Log.d("加载所有Table", String.valueOf(cursor.getCount()));
        if(cursor.getCount() == 0){
            Toast.makeText(context, "当前桌子为空", Toast.LENGTH_SHORT).show();
        }
        ArrayList<Table> tables = new ArrayList<>();
        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex("tableObj");
            int tableNo = cursor.getInt(cursor.getColumnIndex("tableNo"));
            Log.d("桌号", String.valueOf(tableNo));
            byte[] tableInByte = cursor.getBlob(index);
            Table table = byteToTable(tableInByte);
            MainActivity.tables.put(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tableNo"))),table);
        }
        cursor.close();
        return tables;
    }

    private byte[] tableToBytes(Table table){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(table);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Table byteToTable(byte[] data) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Table) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
