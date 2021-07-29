package com.example.caketouch.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.caketouch.Menu.Dish;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ImageDatabaseHandler extends SQLiteOpenHelper {

    Context context;
    private static String DATABASE_NAME = "my.db";
    private static int DATABASE_VERSION = 1;
    private static String createTableQuery = "create table menus (name TEXT, dish BLOB)";

    public ImageDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            db.execSQL(createTableQuery);
            Toast.makeText(context, "SQLite create table.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context, "SQLite create fail." + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void storeDish(Dish dish){
        try{
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//            Bitmap bitmap = model.getImage();
//            ByteArrayOutputStream objectByteArrayOutputStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
//            byte[] imageInByte = objectByteArrayOutputStream.toByteArray();

            byte[] dishInByte = dishToByte(dish);

            ContentValues contentValues = new ContentValues();
            contentValues.put("name", dish.getName());
//            contentValues.put("image", imageInByte);
            contentValues.put("dish", dishInByte);

            long checkIfQueryRun = sqLiteDatabase.insert("menus",null, contentValues);
            if (checkIfQueryRun != 0){
                Toast.makeText(context, "insert Dish success.", Toast.LENGTH_SHORT).show();
//                objectByteArrayOutputStream.close();
            }
            else{
                Toast.makeText(context, "insert dish fail.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context, "fail:" + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteDish(String name){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int res = sqLiteDatabase.delete("menus", name, new String[]{"name="});
        Log.d("删除Dish:", String.valueOf(res));

    }

    public Dish loadDish(String name){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query("menus", new String[]{"name"}, "name=", new String[]{name},null,null, "ID ASC");

        if (cursor.getCount() == 0)return null;

//        int nameColumnIndex = cursor.getColumnIndex("imageName");
//        String strImageName = cursor.getString(nameColumnIndex);

        //int imageColumnIndex = cursor.getColumnIndex("image");
        //ByteArrayInputStream imageStream = new ByteArrayInputStream();
        //byte[] imageInByte = cursor.getBlob(imageColumnIndex);
        //Bitmap image = BitmapFactory.decodeByteArray(imageInByte, 0 ,imageInByte.length);

        int dishColumnIndex = cursor.getColumnIndex("dish");

        byte[] dishInByte = cursor.getBlob(dishColumnIndex);

        return byteToDish(dishInByte);
    }

    public ArrayList<Dish> loadAllDish(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("menus", null, "name=", null,null,null, "ID ASC");
        Log.d("加载所有Dish:", String.valueOf(cursor.getCount()));
        ArrayList<Dish> dishes = new ArrayList<>();
        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex("dish");
            byte[] dishInByte = cursor.getBlob(index);
            dishes.add(byteToDish(dishInByte));
        }
        return dishes;
    }

    public Dish byteToDish(byte[] data) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Dish) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] dishToByte(Dish dish){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(dish);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
