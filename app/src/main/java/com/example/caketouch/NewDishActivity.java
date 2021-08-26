package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.caketouch.menu.Dish;
import com.example.caketouch.menu.DishType;
import com.example.caketouch.menu.DishUnit;
import com.example.caketouch.model.DishDatabaseHandler;
import com.example.caketouch.util.RequestCode;

import java.util.Date;

public class NewDishActivity extends Activity {

    private DishDatabaseHandler databaseHandler;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_new_dish);
        databaseHandler = new DishDatabaseHandler(this);
        //Keyboard hide when loss focus
        LinearLayout linearLayout = findViewById(R.id.addDishLayout);
        linearLayout.setOnTouchListener((v, event) -> {
            linearLayout.setFocusable(true);
            linearLayout.setFocusableInTouchMode(true);
            linearLayout.requestFocus();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(linearLayout.getWindowToken(), 0);
            //curEditText.clearFocus();
            return false;
        });

        ImageView imageView = findViewById(R.id.imageViewDish);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, RequestCode.PHOTO);
        });

        Button buttonCancel = findViewById(R.id.buttonCancelAddDish);
        buttonCancel.setOnClickListener(v->{
            finish();
        });

        //confirm button
        Button buttonConfirm = findViewById(R.id.buttonConfirmAddDish);
        buttonConfirm.setOnClickListener(v -> {
            if(!confirm()){
                return;
            }
            finish();
        });

    }

    public boolean confirm(){
        Bitmap dishBitmap = ((BitmapDrawable)(((ImageView)findViewById(R.id.imageViewDish)).getDrawable())).getBitmap();
        EditText editTextDishName = findViewById(R.id.editTextDishName);
        EditText editTextDishPrice = findViewById(R.id.editTextDishPrice);
        EditText editTextDishLowPrice = findViewById(R.id.editTextDishLowPrice);
        Spinner spinnerUnit = findViewById(R.id.spinnerDishUnit);
        Spinner spinnerType = findViewById(R.id.spinnerDishType);

        if (String.valueOf(editTextDishName.getText()).isEmpty() ||
                String.valueOf(editTextDishPrice.getText()).isEmpty()){
            Toast.makeText(this, "信息未填完", Toast.LENGTH_SHORT).show();
            return false;
        }

        Dish dish = new Dish(String.valueOf(editTextDishName.getText()),
                DishUnit.getDishUnit(spinnerUnit.getSelectedItem().toString()),
                dishBitmap,
                Float.parseFloat(String.valueOf(editTextDishPrice.getText())),
                Float.parseFloat(String.valueOf(editTextDishLowPrice.getText())),
                DishType.getDishType(spinnerType.getSelectedItem().toString()),
                new Date().getTime()
                );
        if (databaseHandler.isExist(dish.getName())){
            Toast.makeText(this, "菜品名字已存在！", Toast.LENGTH_SHORT).show();
            return false;
        }
        this.databaseHandler.storeDish(dish);
        return true;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("图片",String.valueOf(resultCode));
        if (intent == null)return;
        if(requestCode == RequestCode.PHOTO){
            Log.d("图片","保存");

            //Bundle extras = intent.getExtras();
            Log.d("图片", "extra");
            Uri uri = intent.getData();
            resizeImage(uri);

        }
        else if(requestCode == RequestCode.PHOTO_CROP){
            Log.d("图片","裁剪后");
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                Bitmap bitmap = bundle.getParcelable("data");
                ImageView imageView = findViewById(R.id.imageViewDish);
                imageView.setImageBitmap(bitmap);
            }

        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public  void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 16);
        intent.putExtra("aspectY", 9);
        //裁剪的大小
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 180);
        intent.putExtra("return-data", true);
        //设置返回码
        this.startActivityForResult(intent, RequestCode.PHOTO_CROP);
    }

}
