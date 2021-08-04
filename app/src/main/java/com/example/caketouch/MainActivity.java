package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;


import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.caketouch.model.DishDatabaseHandler;
import com.example.caketouch.table.Table;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity implements AddTableDialogFragment.NoticeDialogListener{
    private int tableCount = 0;

    private LinearLayout tables_layout = null;

    public static WeakReference<Context> sContextReference;

    public Map<Integer, Table> tables = new HashMap<>();

    File tmpDir = new File(Environment.getExternalStorageDirectory() + "/mealPic" );

    private DishDatabaseHandler dishDatabaseHandler;

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

        dishDatabaseHandler = new DishDatabaseHandler(this);

        if (!tmpDir.exists()){
            Log.d("新建文件",tmpDir.getAbsolutePath());
            Log.d("文件：",String.valueOf(tmpDir.mkdir()));;
        }else{
            Log.d("文件","已存在");
        }

        Button button = findViewById(R.id.buttonServeFood);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, RequestCode.PHOTO);
            }

        });

        Button settingBtn = findViewById(R.id.buttonSetting);
        settingBtn.setOnClickListener((v)->{
            Intent intent = new Intent("com.example.caketouch.SettingActivity");
            startActivity(intent);
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
            Log.d("图片","触发");
            Log.d("图片",String.valueOf(resultCode));
            if (intent == null)return;
        if(requestCode == RequestCode.PHOTO){
            Log.d("图片","保存");

            //Bundle extras = intent.getExtras();
            Log.d("图片", "extra");
            //Bitmap bm = extras.getParcelable("data");

            //Uri uri = saveBitmap(bm, "zzx.png");
//            Uri uri = intent.getData();
//            resizeImage(uri);
//            uri = convertUri(uri);
            Uri uri = intent.getData();
            resizeImage(uri);
            //启动图像裁剪
//            startImageZoom(uri);

        }
        else if(requestCode == RequestCode.PHOTO_CROP){
            Log.d("图片","裁剪后");
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                Bitmap bitmap = bundle.getParcelable("data");
                //Uri uri = getImageUri(this, bitmap);
                //                    Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //imageDatabaseHandler.storeDish(new Dish("猪脚", "份", bitmap, 60, 55, DishType.yao, new Date().getTime()));
            }

        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪的大小
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        //设置返回码
        this.startActivityForResult(intent, RequestCode.PHOTO_CROP);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);//save image
        return Uri.parse(path);
    }

    private Uri convertUri(Uri uri){
        InputStream is;
        try {
            //Uri ----> InputStream
            is = getContentResolver().openInputStream(uri);
            //InputStream ----> Bitmap
            Bitmap bm = BitmapFactory.decodeStream(is);
            //关闭流
            is.close();
            return saveBitmap(bm, "001.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
        this.startActivityForResult(intent, 12);

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
                //tables.removeView(button);
            }catch (Exception e){

            }

        });
    }



    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
