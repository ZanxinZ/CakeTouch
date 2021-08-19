package com.example.caketouch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class StuffServeActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serve_stuff);

        ImageView back = findViewById(R.id.imageViewServeFoodBack);
        back.setOnClickListener(v -> {
            finish();
        });
    }
}
