package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.caketouch.food_for_serve.DrinkOrdered;
import com.example.caketouch.table.Table;

import java.util.Map;
import java.util.Set;

public class DrinkCheckActivity extends Activity {
    int drinkBlockCount = 0;
    LinearLayout curRow;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_drink);

        ImageView back = findViewById(R.id.imageViewCheckDrinkBack);
        back.setOnClickListener(v -> {
            finish();
        });

        LinearLayout container = findViewById(R.id.checkDrinkContainer);
        loadData(container);


    }
    private void loadData(LinearLayout container){
        for (Map.Entry<Integer, Table> tableEntry:
             MainActivity.tables.entrySet()) {
            Set<Map.Entry<Long, DrinkOrdered>> drinkOrderedMaps = tableEntry.getValue().getOrder().drinkOrderedMap.entrySet();
            addDrinkBlock(container, drinkOrderedMaps, tableEntry.getKey());
            
        }
    }
    @SuppressLint("SetTextI18n")
    private void addDrinkBlock(LinearLayout container, Set<Map.Entry<Long, DrinkOrdered>> drinkOrderedMaps, int tableNo){
        if (drinkBlockCount % 5 == 0){
            curRow = new LinearLayout(this);
            container.addView(curRow);
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        View drinkBlock = inflater.inflate(R.layout.drink_block,null);
        TextView textViewTableNo = drinkBlock.findViewById(R.id.textViewDrinkBlockTableNo);
        textViewTableNo.setText(tableNo + " " + textViewTableNo.getText());
        //ScrollView scrollView =
        LinearLayout linearLayoutDrinkBlocks = drinkBlock.findViewById(R.id.drink_blocks);//drinkSmallBlocks
        linearLayoutDrinkBlocks.removeAllViews();
        for (Map.Entry<Long, DrinkOrdered> drinkOrderedEntry:
             drinkOrderedMaps) {
            DrinkOrdered drinkOrdered = drinkOrderedEntry.getValue();
            View drinkBlockSmall = inflater.inflate(R.layout.drink_block_small, null);
            TextView drinkName = drinkBlockSmall.findViewById(R.id.textViewDrinkBlockSmallName);
            drinkName.setText(drinkOrdered.getName());
            TextView drinkPrice = drinkBlockSmall.findViewById(R.id.textViewDrinkBlockSmallPrice);
            drinkPrice.setText(drinkOrdered.getMoney() + "元");
            TextView drinkCount = drinkBlockSmall.findViewById(R.id.textViewDrinkBlockSmallCount);
            drinkCount.setText("X " + drinkOrdered.getCount() + drinkOrdered.getDishUnit());
            linearLayoutDrinkBlocks.addView(drinkBlockSmall);
        }

        curRow.addView(drinkBlock);
        drinkBlockCount++;
    }

}
