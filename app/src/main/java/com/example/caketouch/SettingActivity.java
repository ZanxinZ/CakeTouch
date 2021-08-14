package com.example.caketouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.caketouch.model.DishDatabaseHandler;

public class SettingActivity extends Activity {
    DishDatabaseHandler databaseHandler;
    @Override
    public void onCreate(Bundle savedInstanceState){
        databaseHandler = new DishDatabaseHandler(this);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_setting);
        Button newDishBtn = findViewById(R.id.buttonAddDish);
        newDishBtn.setOnClickListener((v)->{
            Intent intent = new Intent("com.example.caketouch.NewDishActivity");
            startActivity(intent);
        });

        Button showDishBtn = findViewById(R.id.buttonShowDish);
        showDishBtn.setOnClickListener(v->{
            Intent intent = new Intent("com.example.caketouch.DishShowActivity");
            startActivity(intent);
        });

        Button backButton = findViewById(R.id.buttonSettingBack);
        backButton.setOnClickListener(v->{
            finish();
        });
    }
}
