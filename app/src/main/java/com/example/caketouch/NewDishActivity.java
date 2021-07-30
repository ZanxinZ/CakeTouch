package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class NewDishActivity extends Activity {
    EditText curEditText;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_new_dish);



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
    }

    public void bindEditText(){
        EditText editTextDishName = findViewById(R.id.editTextDishName);
        editTextAddListener(editTextDishName);
        EditText editTextDishPrice = findViewById(R.id.editTextDishPrice);
        editTextAddListener(editTextDishPrice);


    }

    public void editTextAddListener(EditText editText){
        //EditText editTextDishName = findViewById(R.id.editTextDishName);
        editText.setOnClickListener(v -> {
            curEditText = editText;
        });
    }
}
