package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;


import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.caketouch.table.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MainActivity extends Activity implements AddTableDialogFragment.NoticeDialogListener{
    private int tableCount = 0;

    private LinearLayout tables_layout = null;

    public static WeakReference<Context> sContextReference;

    public Map<Integer, Table> tables = new HashMap<>();

    File tmpDir = new File(Environment.getExternalStorageDirectory() + "/mealPic" );



    private int curTableNo;
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
            DialogFragment dialogFragment = new AddTableDialogFragment(tables.keySet());
            dialogFragment.show(getFragmentManager(),"AddTableDialogFragment");

        });

        if (!tmpDir.exists()){
            tmpDir.mkdir();
        }

        Button button = findViewById(R.id.buttonServeFood);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 123);
            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)return;
        if(requestCode == 123){
            Bundle extras = data.getExtras();
            if (extras != null){

                Bitmap bm = extras.getParcelable("data");
                //将Bitmap转化为uri
                Uri uri = saveBitmap(bm,"001.jpg");
                //启动图像裁剪
                startImageZoom(uri);
            }
        }
        else if(requestCode == 12){
            Bundle extras = data.getExtras();
            if (extras != null) {
                //获取到裁剪后的图像
                Bitmap bm = extras.getParcelable("data");
//                ImageView imageView = findViewById(R.id.)
//                mImageView.setImageBitmap(bm);
            }
        }
    }

    private Uri saveBitmap(Bitmap bm, String fileName){
        File img = new File(tmpDir.getAbsolutePath() + "/" + fileName);
        try {
            //打开文件输出流
            FileOutputStream fos = new FileOutputStream(img);
            //将bitmap压缩后写入输出流(参数依次为图片格式、图片质量和输出流)
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            //刷新输出流
            fos.flush();
            //关闭输出流
            fos.close();
            //返回File类型的Uri
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void startImageZoom(Uri uri){
        //构建隐式Intent来启动裁剪程序
        Intent intent = new Intent("com.android.camera.action.CROP");
        //设置数据uri和类型为图片类型
        intent.setDataAndType(uri, "image/*");
        //显示View为可裁剪的
        intent.putExtra("crop", true);
        //裁剪的宽高的比例为1:1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //输出图片的宽高均为150
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        //裁剪之后的数据是通过Intent返回
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 12);
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

        if (dialog.tableNo == -1)return;
        if (dialog.tableNo != 0){
            // User clicked OK button
            Button button = new Button(MainActivity.this);
            button.setId(dialog.tableNo);
            button.setText(button.getId() + " 号位");
            tables_layout.addView(button);
            tableCount++;

            //新增桌子
            Table table = new Table();
            table.setButton(button);
            tables.put(dialog.tableNo, table);
            bindTableBtn(dialog.tableNo);
            curTableNo = dialog.tableNo;
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


    public void bindTableBtn(int tableNo){
        Button button = tables.get(tableNo).getButton();
        button.setOnClickListener((v)->{
            LinearLayout tables = findViewById(R.id.tables);
            try{
                tables.removeView(button);
            }catch (Exception e){

            }

        });
    }

}
