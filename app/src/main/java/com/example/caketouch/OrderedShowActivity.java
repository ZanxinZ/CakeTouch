package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caketouch.food_for_serve.AllOrdered;
import com.example.caketouch.food_for_serve.FoodOrdered;
import com.example.caketouch.food_for_serve.TableOrdered;
import com.example.caketouch.table.Food;
import com.example.caketouch.table.Order;
import com.example.caketouch.table.Stuff;
import com.example.caketouch.table.Table;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public class OrderedShowActivity extends Activity {
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


    private void addFoodBlock(LinearLayout food_blocks, FoodOrdered foodOrdered){
        if (foodCount % 5 == 0){
            //ScrollView scrollView = new ScrollView(this);
            curFoodRow = new LinearLayout(this);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            curFoodRow.setLayoutParams(params);
            curFoodRow.setOrientation(LinearLayout.HORIZONTAL);
//            scrollView.addView(curFoodRow);
//            scrollView.arrowScroll(View.FOCUS_RIGHT);
            food_blocks.addView(curFoodRow);
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        View table_block = inflater.inflate(R.layout.food_block, null);
        curFoodRow.addView(table_block);
        foodCount++;
    }


    public void addToFoodOrderedMap(Food food, int tableNo){
        if (AllOrdered.foodOrderedMap.containsKey(food.getDishNo())){
            //food card has been created
            FoodOrdered foodOrdered = AllOrdered.foodOrderedMap.get(food.getDishNo());
            assert foodOrdered != null;
            foodOrdered.attachTableToFood(tableNo);
        }else{
            FoodOrdered foodOrdered = new FoodOrdered(food.getName());
            foodOrdered.attachTableToFood(tableNo);
            AllOrdered.foodOrderedMap.put(food.getDishNo(),foodOrdered);
        }
    }

    public void addToTableOrderedMap(Stuff stuff, Table table){
        int tableNo = table.getButton().getId();
        TableOrdered tableOrdered = null;
        if (AllOrdered.tableOrderedMap.containsKey(tableNo)){
            tableOrdered = AllOrdered.tableOrderedMap.get(tableNo);
            assert tableOrdered != null;
            tableOrdered.attachStuffToTable(stuff);
            tableOrdered.setPeople(table.getPeople());


        }else {
            tableOrdered = new TableOrdered(table.getPeople());
            tableOrdered.attachStuffToTable(stuff);
            AllOrdered.tableOrderedMap.put((long) tableNo, tableOrdered);
        }

        if(stuff.isServed()){
            tableOrdered.setServedCount(tableOrdered.getServedCount() + 1);
        }else{
            tableOrdered.setNotServedCount(tableOrdered.getNotServedCount() + 1);
        }

    }

    public void loadData(){
        Set<Map.Entry<Integer, Table>> entrySet = MainActivity.tables.entrySet();

        AllOrdered.foodOrderedMap.clear();
        AllOrdered.tableOrderedMap.clear();

        for (Map.Entry<Integer,Table> entry:
             entrySet) {
            Order order = entry.getValue().getOrder();
            for (Map.Entry<Long, Stuff> foodEntry:
                 order.ordered.entrySet()) {
                Stuff stuff = foodEntry.getValue();
                if (!stuff.isServed()){
                    addToFoodOrderedMap(new Food(stuff, null) , entry.getKey());
                }
                addToTableOrderedMap(stuff, entry.getValue());
            }
        }

    }

    private void constructTableBlocks(){
        Set<Map.Entry<Long, TableOrdered>> tableOrderedEntrySet = AllOrdered.tableOrderedMap.entrySet();
        for (Map.Entry<Long, TableOrdered> entry:
             tableOrderedEntrySet) {
            Log.d(" 点餐",String.valueOf(entry.getValue().getStuffs().size()));
            addTableBlock(linearLayoutTableBlocks, entry.getValue(), entry.getKey().intValue());
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

}
