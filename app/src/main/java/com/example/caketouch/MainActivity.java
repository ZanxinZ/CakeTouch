package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MainActivity extends Activity {
    private int tableCount = 0;
    private MainActivity cur = this;
    private LinearLayout tables_layout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tables_layout = (LinearLayout) findViewById(R.id.tables);
        initData();
    }
    private void initData(){
        Button addTableBtn = findViewById(R.id.buttonAddTable);

        addTableBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
//                Button button = new Button(cur);
//                button.setId(++tableCount);
//                button.setText(Integer.toString(button.getId()));
//                tables_layout.addView(button);
//                ScrollView scrollView = findViewById(R.id.tableScroll);
//                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
