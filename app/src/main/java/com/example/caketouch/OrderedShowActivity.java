package com.example.caketouch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class OrderedShowActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_show);

        ImageView back = findViewById(R.id.imageViewOrderedShowBack);
        back.setOnClickListener(v->{
            finish();
        });
    }
}
