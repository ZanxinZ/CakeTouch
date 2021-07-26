package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;


public class MainActivity extends Activity implements AddTableDialogFragment.NoticeDialogListener{
    private int tableCount = 0;

    private LinearLayout tables_layout = null;

    public static WeakReference<Context> sContextReference;

    public Set<Integer> tableNos = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tables_layout = (LinearLayout) findViewById(R.id.tables);
        sContextReference = new WeakReference<>(this);

        initData();
    }
    private void initData(){

        Button addTableBtn = findViewById(R.id.buttonAddTable);

        addTableBtn.setOnClickListener(v -> {
            DialogFragment dialogFragment = new AddTableDialogFragment(tableNos);
            dialogFragment.show(getFragmentManager(),"AddTableDialogFragment");

        });


    }

    public static void scrollToBottom(final View scroll, final View innerView) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                if (scroll == null || innerView == null) { return; }
                int offset = innerView.getMeasuredHeight() - scroll.getHeight();
                if (offset < 0)
                    offset = 0 ;
            scroll.scrollTo(0, offset);
            } });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDialogPositiveClick(AddTableDialogFragment dialog) {

        if (dialog.tableNo != 0){
            // User clicked OK button
            Button button = new Button(MainActivity.this);
            button.setId(dialog.tableNo);
            button.setText(button.getId() + " 号位");
            tables_layout.addView(button);
            tableCount++;
            tableNos.add(dialog.tableNo);
            dialog.tableNo = 0;
            ScrollView scrollView = findViewById(R.id.tableScroll);
            scrollToBottom(scrollView, tables_layout);
            Log.d("添加桌位", "成功!");
            Toast.makeText(getApplicationContext(), "添加桌位：成功！< " + button.getText() + " >" , Toast.LENGTH_LONG).show();
            return ;
        }



        Log.d("添加桌位", "失败");
        Toast.makeText(getApplicationContext(), "新增桌位失败", Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onDialogNegativeClick(AddTableDialogFragment dialog) {
//        Toast.makeText(sContextReference.get(), "hh", Toast.LENGTH_LONG);

    }


}
