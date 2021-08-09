package com.example.caketouch.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.caketouch.Menu.Dish;
import com.example.caketouch.Menu.DishType;
import com.example.caketouch.Menu.Menu;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DishDatabaseHandler extends SQLiteOpenHelper {

    Context context;
    private static String DATABASE_NAME = "my.db";
    private static int DATABASE_VERSION = 1;
    private static String createTableQuery = "create table menus (name TEXT, dish BLOB)";

    public DishDatabaseHandler(@Nullable Context context) {
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
        int res = sqLiteDatabase.delete("menus", "name=?", new String[]{name});
        Log.d("删除Dish:", String.valueOf(res));

    }

    public Dish loadDish(String name){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query("menus", new String[]{"dish"}, "name=?", new String[]{name},null,null, null);
        Log.d("查询",String.valueOf(cursor.getCount()));
        if (cursor.getCount() == 0){
            Toast.makeText(context, "Dish NotFound", Toast.LENGTH_SHORT).show();
            return null;
        }

//        int nameColumnIndex = cursor.getColumnIndex("imageName");
//        String strImageName = cursor.getString(nameColumnIndex);

        //int imageColumnIndex = cursor.getColumnIndex("image");
        //ByteArrayInputStream imageStream = new ByteArrayInputStream();
        //byte[] imageInByte = cursor.getBlob(imageColumnIndex);
        //Bitmap image = BitmapFactory.decodeByteArray(imageInByte, 0 ,imageInByte.length);



        Dish dish = null;
        if (cursor.moveToNext()){
            int index = cursor.getColumnIndexOrThrow("dish");
            Log.d("索引",String.valueOf(index));
            byte[] dishInByte = cursor.getBlob(index);
            dish = byteToDish(dishInByte);
        }

        cursor.close();
        return dish;
    }

    public ArrayList<Dish> loadAllDish(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("menus", null, null, null,null,null,null);
        Log.d("加载所有Dish", String.valueOf(cursor.getCount()));
        ArrayList<Dish> dishes = new ArrayList<>();
        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex("dish");
            byte[] dishInByte = cursor.getBlob(index);
            Dish dish = byteToDish(dishInByte);
            //dishes.add(dish);
            addDishToMenu(dish);
        }
        cursor.close();
        return dishes;
    }

    private void addDishToMenu(Dish dish){
        switch (dish.getDishType()){
            case other:
                if (!Menu.other.containsKey(dish.getDishNo()))
                Menu.other.put(dish.getDishNo(), dish);
                break;
            case yao:
                if (!Menu.yao.containsKey(dish.getDishNo()))
                Menu.yao.put(dish.getDishNo(), dish);
                break;
            case soup:
                if (!Menu.soup.containsKey(dish.getDishNo()))
                Menu.soup.put(dish.getDishNo(), dish);
                break;
            case saute:
                if (!Menu.saute.containsKey(dish.getDishNo()))
                Menu.saute.put(dish.getDishNo(), dish);
                break;
            case pot:
                if (!Menu.pot.containsKey(dish.getDishNo()))
                Menu.pot.put(dish.getDishNo(), dish);
                break;
            case fry:
                if (!Menu.fry.containsKey(dish.getDishNo()))
                Menu.fry.put(dish.getDishNo(), dish);
                break;
            case drink:
                if (!Menu.drink.containsKey(dish.getDishNo()))
                Menu.drink.put(dish.getDishNo(), dish);
                break;

        }
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
