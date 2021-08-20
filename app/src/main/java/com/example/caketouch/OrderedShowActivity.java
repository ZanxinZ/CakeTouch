package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.caketouch.table.Food;
import com.example.caketouch.table.Stuff;
import com.example.caketouch.table.Table;

import java.io.InputStream;
import java.util.zip.Inflater;

public class OrderedShowActivity extends Activity {
    int tableCount = 0;
    LinearLayout curTableBlockRow;

    int foodCount = 0;
    LinearLayout curFoodRow;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_show);

        ImageView back = findViewById(R.id.imageViewOrderedShowBack);
        back.setOnClickListener(v->{
            finish();
        });
        LinearLayout linearLayout = findViewById(R.id.table_blocks);
        TextView textView = findViewById(R.id.textViewTableSite);
        textView.setOnClickListener(v -> {
            addTableBlock(linearLayout, new Table());
        });

        LinearLayout linearLayoutFoodBlocks = findViewById(R.id.food_blocks);
        TextView waitForServe = findViewById(R.id.textViewFoodWaitForServe);
        waitForServe.setOnClickListener(v -> {
            Resources r = OrderedShowActivity.this.getResources();
            @SuppressLint("ResourceType") InputStream is = r.openRawResource(R.drawable.logo);
            BitmapDrawable bmpDraw = new BitmapDrawable(is);
            Bitmap bitmap = bmpDraw.getBitmap();
            //Bitmap bitmap = BitmapFactory.decodeStream(is);
            addFoodBlock(linearLayoutFoodBlocks, new Food("电池", "个", 25, new Long(12522) , new Long(55896), 2, bitmap));
        });
    }
    private void addTableBlock(LinearLayout table_blocks, Table table){
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
        curTableBlockRow.addView(table_block);
        tableCount++;
    }


    private void addFoodBlock(LinearLayout food_blocks, Food food){
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


    private int autoDp(int dp){
        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()));
    }

}
