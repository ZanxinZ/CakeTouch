package com.example.caketouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SettingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_setting);
        Button newDishBtn = findViewById(R.id.buttonAddDish);
        newDishBtn.setOnClickListener((v)->{
            Intent intent = new Intent("com.example.caketouch.NewDishActivity");
            startActivity(intent);
        });
    }
}
