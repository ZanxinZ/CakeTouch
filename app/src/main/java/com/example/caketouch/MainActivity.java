package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;


import android.app.AlertDialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.caketouch.fragment.AddStuffDialogFragment;
import com.example.caketouch.fragment.AddTableDialogFragment;
import com.example.caketouch.menu.Dish;
import com.example.caketouch.menu.DishType;
import com.example.caketouch.menu.DishUnit;
import com.example.caketouch.model.DishDatabaseHandler;
import com.example.caketouch.model.OrderDataBaseHandler;
import com.example.caketouch.table.Table;
import com.example.caketouch.util.BluetoothUtil;
import com.example.caketouch.util.PrinterUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class MainActivity extends Activity implements AddTableDialogFragment.NoticeDialogListener, AddStuffDialogFragment.NoticeDialogListener{
    private int tableCount = 0;
    private static String blue = "#4795EC";
    private static String light_blue = "#D3ECFA";
    private static String table_text_color = "#FF000000";
    private static String table_text_color_chosen = "#FFFFFFFF";
    private LinearLayout tables_layout = null;

    public static WeakReference<Context> sContextReference;

    public static Map<Integer, Table> tables = new TreeMap<>();//tableNo, table


    public Button chooseTableBtn = null;


    File tmpDir = new File(Environment.getExternalStorageDirectory() + "/mealPic" );

    private DishDatabaseHandler dishDatabaseHandler;
    private OrderDataBaseHandler orderDataBaseHandler;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tables_layout = findViewById(R.id.tables);
        sContextReference = new WeakReference<>(this);
        initData();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData(){

        Button addTableBtn = findViewById(R.id.buttonAddTable);

        addTableBtn.setOnClickListener(v -> {
            tableLossFocus();
            DialogFragment dialogFragment = new AddTableDialogFragment(tables.keySet());
            dialogFragment.show(getFragmentManager(),"AddTableDialogFragment");

        });

        dishDatabaseHandler = new DishDatabaseHandler(this);
        orderDataBaseHandler= new OrderDataBaseHandler(this);
        dishDatabaseHandler.loadAllDish();



        if (!tmpDir.exists()){
            Log.d("新建文件",tmpDir.getAbsolutePath());
            Log.d("文件：",String.valueOf(tmpDir.mkdir()));;
        }else{
            Log.d("文件","已存在");
        }

        Button checkFoodBtn = findViewById(R.id.buttonCheckOrder);
        checkFoodBtn.setOnClickListener(v -> {
            tableLossFocus();
            Intent intent = new Intent("com.example.caketouch.OrderedShowActivity");
            startActivity(intent);
        });

        Button serveFoodBtn = findViewById(R.id.buttonServeFood);
        serveFoodBtn.setOnClickListener(v -> {
            tableLossFocus();
            Intent intent = new Intent("com.example.caketouch.StuffServeActivity");
            startActivity(intent);
        });

        Button settingBtn = findViewById(R.id.buttonSetting);
        settingBtn.setOnClickListener((v)->{
            tableLossFocus();
            Intent intent = new Intent("com.example.caketouch.SettingActivity");
            startActivity(intent);
        });
        Button saveOrderBtn = findViewById(R.id.buttonSaveOrder);
        saveOrderBtn.setOnClickListener(v->{

            if (chooseTableBtn == null){
                Toast.makeText(MainActivity.this, "选择桌子之后再打印。", Toast.LENGTH_SHORT).show();
                return;
            }
            int tableNo = chooseTableBtn.getId();
            Table table = tables.get(tableNo);
            orderDataBaseHandler.updateTable(table, tableNo);

            List<BluetoothDevice> devices = BluetoothUtil.getPairedPrinterDevices();//Find all printer.
            //choose the first one
            if (devices.size() != 0){
                try{
                    BluetoothSocket socket = BluetoothUtil.connectDevice(devices.get(0));
                    PrinterUtil.printOrder(socket, BitmapFactory.decodeResource(this.getResources(), R.drawable.alipay),BitmapFactory.decodeResource(this.getResources(), R.drawable.wechat), table);
                    Toast.makeText(MainActivity.this, "打印好了！", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    e.printStackTrace();
                    builder.setTitle("提示")
                            .setMessage("连接失败，请打开蓝牙打印机~")
                            .setPositiveButton("好的", (dialog, id) -> {
                        // User clicked OK button
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }else{
                Toast.makeText(MainActivity.this, "找不到设备，请打开蓝牙并与打印机连接。", Toast.LENGTH_LONG).show();
            }


        });
        loadData();
        BluetoothUtil.init(this);
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

        if (dialog.tableNo == 0){
            Log.d("添加桌位", "失败");
            Toast.makeText(MainActivity.this, "桌位号不能是0", Toast.LENGTH_LONG).show();
            return;
        }
        if (dialog.tableNo != -1 && dialog.peopleCount == -1){
            Toast.makeText(MainActivity.this, "人数未填。", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dialog.tableNo == -1)return;//has done nothing



        // User clicked OK button
        Button button = new Button(MainActivity.this);
        button.setId(dialog.tableNo);

        button.setText(button.getId() + " 号位" + dialog.peopleCount + "人");
        button.setGravity(Gravity.CENTER);
        button.setTextSize(autoDp(6));
        button.setTextColor(Color.parseColor(table_text_color));
        button.setBackgroundColor(Color.parseColor(light_blue));
        tables_layout.addView(button);
        tableCount++;

        //新增桌子
        Table table = new Table();
        table.setTableNo(dialog.tableNo);
        table.setPeople(dialog.peopleCount);
        tables.put(dialog.tableNo, table);
        bindTableBtn(button, dialog.tableNo);
        dialog.tableNo = 0;
        ScrollView scrollView = findViewById(R.id.tableScroll);
        scrollToBottom(scrollView, tables_layout);

        orderDataBaseHandler.storeTable(table,table.getTableNo());

        Log.d("添加桌位", "成功!");
        Toast.makeText(MainActivity.this, "添加桌位：成功！< " + button.getText() + " >" , Toast.LENGTH_LONG).show();
        return ;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onDialogNegativeClick(AddTableDialogFragment dialog) {
//        Toast.makeText(sContextReference.get(), "hh", Toast.LENGTH_LONG);

    }

    private void loadData(){
        tables_layout.removeAllViews();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (tables.size() == 0){
            orderDataBaseHandler.loadAllTable();
        }

        for (Map.Entry<Integer, Table> tableEntry:
             tables.entrySet()) {
            addOneTableBtnToView(tableEntry.getKey());
        }

    }

    @SuppressLint("SetTextI18n")
    private Button addOneTableBtnToView(int tableNo){
        Button button = new Button(this);
        //((ViewGroup)button.getParent()).removeView(button);
        button.setId(tableNo);
        int tableNum = button.getId();
        button.setText(button.getId() + " 号位" + tables.get(tableNum).getPeople() + "人");
        button.setGravity(Gravity.CENTER);
        button.setTextSize(autoDp(6));
        button.setTextColor(Color.parseColor(table_text_color));
        button.setBackgroundColor(Color.parseColor(light_blue));
        tables_layout.addView(button);
        bindTableBtn(button, tableNo);
        return button;
    }



    public void bindTableBtn(Button button, int tableNo){

        button.setOnClickListener((v)->{
            if (chooseTableBtn != null){
                chooseTableBtn.setBackgroundColor(Color.parseColor(light_blue));
                chooseTableBtn.setGravity(Gravity.CENTER);
                chooseTableBtn.setTextSize(autoDp(6));
                chooseTableBtn.setTextColor(Color.parseColor(table_text_color));
            }
            button.setBackgroundColor(Color.parseColor(blue));
            //button.setGravity(Gravity.RIGHT);
            button.setTextSize(autoDp(8));
            button.setTextColor(Color.parseColor(table_text_color_chosen));
            chooseTableBtn = button;

            //button.setGravity(Gravity.CENTER);
            View view = findViewById(R.id.view_dishes_for_order);
            ScrollView scrollView = (ScrollView)findViewById(R.id.scrollViewDishForOrder);
            scrollView.smoothScrollTo(0,0);
            ChooseDishViewBuilder chooseDishViewBuilder = new ChooseDishViewBuilder(MainActivity.this, (ViewGroup) view, chooseTableBtn);
        });

        button.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("警告！" )
                    .setMessage("确认删除这个桌子吗？")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tables_layout.removeView(button);
                            tables.remove(tableNo);
                            View view = findViewById(R.id.view_dishes_for_order);
                            ((ViewGroup)view).removeAllViews();
                            orderDataBaseHandler.deleteTable(tableNo);
                            tableCount--;
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
            return true;
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

    private int autoDp(int dp){
        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()));
    }


    @Override
    public void onDialogPositiveClick(Dish dish, boolean isNormal, int count, int tableNo) {
        //Toast.makeText(this, String.valueOf(count), Toast.LENGTH_SHORT).show();
        Table table = tables.get(tableNo);
        table.orderStuff(dish, isNormal, count);

        OrderDataBaseHandler orderDataBaseHandler = new OrderDataBaseHandler(MainActivity.this);
        orderDataBaseHandler.updateTable(table, tableNo);
        Toast.makeText(this, "添加"+ count + DishUnit.getUnitStr(dish.getUnit()) + "[" + dish.getName() + "]", Toast.LENGTH_SHORT).show();
        //tables.put(tableNo,table);
    }

    public void tableLossFocus(){
        if (chooseTableBtn != null){
            chooseTableBtn.setBackgroundColor(Color.parseColor(light_blue));
            chooseTableBtn.setGravity(Gravity.CENTER);
            chooseTableBtn.setTextSize(autoDp(6));
            chooseTableBtn.setTextColor(Color.parseColor(table_text_color));
            chooseTableBtn = null;
        }
        LinearLayout linearLayout = findViewById(R.id.view_dishes_for_order);
        linearLayout.removeAllViews();
    }



}
