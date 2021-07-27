package com.example.caketouch.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class ImageDatabaseHandler extends SQLiteOpenHelper {

    Context context;
    private static String DATABASE_NAME = "my.db";
    private static int DATABASE_VERSION = 1;
    private static String createTableQuery = "create table menus (imageName TEXT, image BLOB)";

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

    public void storeImage(ImageModel imageModel){
        try{
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Bitmap bitmap = imageModel.getImage();
            ByteArrayOutputStream objectByteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
            byte[] imageInByte = objectByteArrayOutputStream.toByteArray();
            ContentValues contentValues = new ContentValues();
            contentValues.put("imageName", imageModel.getImageName());
            contentValues.put("image", imageInByte);
            long checkIfQueryRun = sqLiteDatabase.insert("menus",null, contentValues);
            if (checkIfQueryRun != 0){
                Toast.makeText(context, "insert image success.", Toast.LENGTH_SHORT).show();
                objectByteArrayOutputStream.close();
            }
            else{
                Toast.makeText(context, "insert image fail.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context, "fail:" + e, Toast.LENGTH_SHORT).show();
        }
    }

}
