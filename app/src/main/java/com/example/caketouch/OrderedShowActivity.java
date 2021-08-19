package com.example.caketouch;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.caketouch.table.Food;
import com.example.caketouch.table.Stuff;
import com.example.caketouch.table.Table;

import java.util.zip.Inflater;

public class OrderedShowActivity extends Activity {
    int tableCount = 0;
    LinearLayout curTableBlockRow;
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

    }


    private int autoDp(int dp){
        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()));
    }

}
