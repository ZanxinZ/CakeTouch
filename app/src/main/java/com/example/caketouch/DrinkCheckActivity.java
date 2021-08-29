package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    int rowCount;
    LinearLayout linearLayoutDrinkSmall;
    LinearLayout linearLayoutDrinkBlock;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_drink);

        ImageView back = findViewById(R.id.imageViewCheckDrinkBack);
        back.setOnClickListener(v -> {
            finish();
        });

        loadData();

        LinearLayout container = findViewById(R.id.checkDrinkContainer);

        TextView textView = findViewById(R.id.textViewCheckDrink);
        textView.setOnClickListener(v->{
            addDrinkBlock(container,5);
        });

        //constructView();

    }
    private void loadData(){
        for (Map.Entry<Integer, Table> tableEntry:
             MainActivity.tables.entrySet()) {
            Set<Map.Entry<Long, DrinkOrdered>> drinkOrderedMaps = tableEntry.getValue().getOrder().drinkOrderedMap.entrySet();
            for (Map.Entry<Long, DrinkOrdered> drinkOrderEntry:
                 drinkOrderedMaps) {

            }
        }
    }
    private void addDrinkSmallBlock(){

    }
    @SuppressLint("SetTextI18n")
    private void addDrinkBlock(LinearLayout container, int tableNo){
        if (drinkBlockCount % 5 == 0){
            curRow = new LinearLayout(this);

            container.addView(curRow);
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        View drinkBlock = inflater.inflate(R.layout.drink_block,null);
        TextView textViewTableNo = drinkBlock.findViewById(R.id.textViewDrinkBlockTableNo);
        textViewTableNo.setText(tableNo + " " + textViewTableNo.getText());
        LinearLayout linearLayoutDrinkBlocks = drinkBlock.findViewById(R.id.drink_blocks);//drinkSmallBlocks

        curRow.addView(drinkBlock);
        drinkBlockCount++;
    }
}
