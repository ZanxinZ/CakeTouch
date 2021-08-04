package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.caketouch.Menu.Dish;
import com.example.caketouch.Menu.DishType;
import com.example.caketouch.model.DishDatabaseHandler;

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

        Button buttonCancel = findViewById(R.id.buttonCancelAddDish);
        buttonCancel.setOnClickListener(v->{
            Intent intent = new Intent(this, SettingActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        //confirm button
        Button buttonConfirm = findViewById(R.id.buttonConfirmAddDish);
        buttonConfirm.setOnClickListener(v -> {
            confirm();
            Intent intent = new Intent(this, SettingActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

    }

    public void confirm(){
        Bitmap dishBitmap = findViewById(R.id.imageViewDish).getDrawingCache();
        EditText editTextDishName = findViewById(R.id.editTextDishName);
        EditText editTextDishPrice = findViewById(R.id.editTextDishPrice);
        EditText editTextDishLowPrice = findViewById(R.id.editTextDishLowPrice);
        Spinner spinnerUnit = findViewById(R.id.spinnerDishUnit);
        Spinner spinnerType = findViewById(R.id.spinnerDishType);

        Dish dish = new Dish(String.valueOf(editTextDishName.getText()),
                String.valueOf(spinnerUnit.getSelectedItem().toString()),
                dishBitmap,
                Float.parseFloat(String.valueOf(editTextDishPrice.getText())),
                Float.parseFloat(String.valueOf(editTextDishLowPrice.getText())),
                DishType.getDishType(spinnerType.getSelectedItem().toString()),
                new Date().getTime()
                );
        this.databaseHandler.storeDish(dish);


    }

}
