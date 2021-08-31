package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caketouch.food_for_serve.AllOrdered;
import com.example.caketouch.food_for_serve.FoodOrdered;
import com.example.caketouch.food_for_serve.TableOrdered;
import com.example.caketouch.fragment.ServeFoodDialogFragment;
import com.example.caketouch.fragment.TableDetailDialogFragment;
import com.example.caketouch.menu.Dish;
import com.example.caketouch.menu.Menu;
import com.example.caketouch.table.Food;
import com.example.caketouch.table.Order;
import com.example.caketouch.table.Stuff;
import com.example.caketouch.table.Table;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public class OrderedShowActivity extends Activity implements TableDetailDialogFragment.NoticeDialogListener, ServeFoodDialogFragment.NoticeDialogListener {
    int tableCount = 0;
    LinearLayout curTableBlockRow;

    int foodCount = 0;
    LinearLayout curFoodRow;

    LinearLayout linearLayoutTableBlocks;
    LinearLayout linearLayoutFoodBlocks;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_show);

        ImageView back = findViewById(R.id.imageViewOrderedShowBack);
        back.setOnClickListener(v->{
            finish();
        });
        linearLayoutTableBlocks = findViewById(R.id.table_blocks);
//        TextView textView = findViewById(R.id.textViewTableSite);
//        textView.setOnClickListener(v -> {
//            addTableBlock(linearLayout, new Table());
//        });

        linearLayoutFoodBlocks = findViewById(R.id.food_blocks);
//        TextView waitForServe = findViewById(R.id.textViewFoodWaitForServe);
//        waitForServe.setOnClickListener(v -> {
//            Resources r = OrderedShowActivity.this.getResources();
//            @SuppressLint("ResourceType") InputStream is = r.openRawResource(R.drawable.logo);
//            BitmapDrawable bmpDraw = new BitmapDrawable(is);
//            Bitmap bitmap = bmpDraw.getBitmap();
//            //Bitmap bitmap = BitmapFactory.decodeStream(is);
//            addFoodBlock(linearLayoutFoodBlocks, new Food("电池", "个", 25, new Long(12522) , new Long(55896), bitmap));
//        });

        loadData();
        constructTableBlocks();
        constructStuffBlocks();
    }
    @SuppressLint("SetTextI18n")
    private void addTableBlock(LinearLayout table_blocks, TableOrdered tableOrdered, int tableNo){
        if (tableCount % 4 == 0){
            curTableBlockRow = new LinearLayout(this);
            curTableBlockRow.setOrientation(LinearLayout.HORIZONTAL);
            table_blocks.addView(curTableBlockRow);
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        View table_block = inflater.inflate(R.layout.table_block, null);
        table_block.setOnClickListener(v->{
            DialogFragment dialogFragment = new TableDetailDialogFragment(tableNo, tableOrdered);
            dialogFragment.show(getFragmentManager(),"TableDetailDialogFragment");
        });
        //table_block.setPaddingRelative(autoDp(10), autoDp(10), autoDp(10), autoDp(10));
        //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) table_block.findViewById(R.id.).getLayoutParams();
        //params.setMargins(autoDp(10),autoDp(10),autoDp(10),autoDp(10));
        //table_block.setLayoutParams(params);
        TextView textViewTableNo = table_block.findViewById(R.id.textViewTableNo);
        textViewTableNo.setText(tableNo + " " +textViewTableNo.getText());
        TextView textViewPeople = table_block.findViewById(R.id.textViewTablePeopleCount);
        textViewPeople.setText(String.valueOf(tableOrdered.getPeople()));
        TextView textViewOrdered = table_block.findViewById(R.id.textViewTableStuffOrdered);
        textViewOrdered.setText(String.valueOf(tableOrdered.getNotServedCount()));
        TextView textViewServed = table_block.findViewById(R.id.textViewTableStuffServed);
        textViewServed.setText(String.valueOf(tableOrdered.getServedCount()));

        curTableBlockRow.addView(table_block);
        tableCount++;
    }


    @SuppressLint("SetTextI18n")
    private void addFoodBlock(LinearLayout food_blocks, FoodOrdered foodOrdered){
        if (foodCount % 5 == 0){
            //ScrollView scrollView = new ScrollView(this);
            curFoodRow = new LinearLayout(this);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            curFoodRow.setLayoutParams(params);
            curFoodRow.setOrientation(LinearLayout.HORIZONTAL);
//            curFoodRow.setGravity(Gravity.CENTER);
//            scrollView.addView(curFoodRow);
//            scrollView.arrowScroll(View.FOCUS_RIGHT);
            food_blocks.addView(curFoodRow);
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        View table_block = inflater.inflate(R.layout.food_block, null);
        Bitmap bitmap = null;
        Dish dish = Menu.findDish(foodOrdered.getDishNo());
        if (dish == null){
            Resources r = OrderedShowActivity.this.getResources();
            @SuppressLint("ResourceType") InputStream is = r.openRawResource(R.drawable.logo);
            BitmapDrawable bmpDraw = new BitmapDrawable(is);
            bitmap = bmpDraw.getBitmap();
        }
        else{
            bitmap = dish.getImageInBitmap();
        }
        ImageView image = table_block.findViewById(R.id.imageViewFoodBlock);
        image.setImageBitmap(bitmap);
        TextView name = table_block.findViewById(R.id.textViewFoodBlockName);
        name.setText(foodOrdered.getFoodName());
        TextView tableCount = table_block.findViewById(R.id.textViewFoodBlockTableCount);
        tableCount.setText(foodOrdered.getTablesOrdered().size() + " " + tableCount.getText() );
        Button serveFoodButton = table_block.findViewById(R.id.buttonServeOneFood);
        serveFoodButton.setOnClickListener(v -> {
            ServeFoodDialogFragment dialog = new ServeFoodDialogFragment(foodOrdered);
            dialog.show(getFragmentManager(), "ServeFoodDialogFragment");
        });

        curFoodRow.addView(table_block);
        foodCount++;
    }


    public void addToFoodOrderedMap(Stuff stuff, int tableNo){
        if (AllOrdered.foodOrderedMap.containsKey(stuff.getDishNo())){
            //food card has been created
            FoodOrdered foodOrdered = AllOrdered.foodOrderedMap.get(stuff.getDishNo());
            assert foodOrdered != null;
            foodOrdered.attachTableToFood(tableNo, stuff.getID());
        }else{
            FoodOrdered foodOrdered = new FoodOrdered(stuff.getName(), stuff.getDishNo());
            foodOrdered.attachTableToFood(tableNo,stuff.getID());
            AllOrdered.foodOrderedMap.put(stuff.getDishNo(),foodOrdered);
        }
    }

    public void addToTableOrderedMap(Stuff stuff, Table table){
        int tableNo = table.getButton().getId();
        TableOrdered tableOrdered = null;
        if (AllOrdered.tableOrderedMap.containsKey(tableNo)){
            tableOrdered = AllOrdered.tableOrderedMap.get(tableNo);
            assert tableOrdered != null;
            tableOrdered.attachStuffToTable(stuff);
        }else {
            tableOrdered = new TableOrdered(table.getPeople());
            tableOrdered.attachStuffToTable(stuff);
            AllOrdered.tableOrderedMap.put(tableNo, tableOrdered);
        }

        if(stuff.isServed()){
            tableOrdered.setServedCount(tableOrdered.getServedCount() + 1);
        }else{
            tableOrdered.setNotServedCount(tableOrdered.getNotServedCount() + 1);
        }

    }

    /**
     * load Data from tables, and set data to "AllOrdered".
     */
    public void loadData(){
        Set<Map.Entry<Integer, Table>> entrySet = MainActivity.tables.entrySet();

        AllOrdered.foodOrderedMap.clear();
        AllOrdered.tableOrderedMap.clear();

        for (Map.Entry<Integer,Table> entry:
             entrySet) {
            Order order = entry.getValue().getOrder();
            Log.d("数量Map", String.valueOf(order.ordered.size()));
            for (Map.Entry<Long, Stuff> stuffEntry:
                 order.ordered.entrySet()) {
                Stuff stuff = stuffEntry.getValue();
                if (!stuff.isServed()){
                    addToFoodOrderedMap(stuff , entry.getKey());
                }
                addToTableOrderedMap(stuff, entry.getValue());

            }
        }

        Log.d("数量Map", String.valueOf(AllOrdered.tableOrderedMap.size()));

    }

    private void constructTableBlocks(){
        Set<Map.Entry<Integer, TableOrdered>> tableOrderedEntrySet = AllOrdered.tableOrderedMap.entrySet();
        for (Map.Entry<Integer, TableOrdered> entry:
             tableOrderedEntrySet) {
            //Log.d(" 点餐",String.valueOf(entry.getValue().getStuffs().size()));
            addTableBlock(linearLayoutTableBlocks, entry.getValue(), entry.getKey());
        }
    }
    private void constructStuffBlocks(){
        Set<Map.Entry<Long, FoodOrdered>> foodOrderedEntrySet = AllOrdered.foodOrderedMap.entrySet();
        for (Map.Entry<Long, FoodOrdered> entry:
                foodOrderedEntrySet) {
            addFoodBlock(linearLayoutFoodBlocks, entry.getValue());
        }
    }

    private int autoDp(int dp){
        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()));
    }


    @Override
    public void onFoodServed() {
        tableCount = 0;
        curTableBlockRow = null;
        foodCount = 0;
        curFoodRow = null;
        linearLayoutTableBlocks.removeAllViews();
        linearLayoutFoodBlocks.removeAllViews();
        loadData();
        constructTableBlocks();
        constructStuffBlocks();
    }
}
