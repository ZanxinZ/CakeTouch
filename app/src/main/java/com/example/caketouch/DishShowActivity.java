package com.example.caketouch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.caketouch.Menu.Dish;
import com.example.caketouch.Menu.Menu;

import java.util.HashMap;
import java.util.Map;

public class DishShowActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstateState){
        super.onCreate(savedInstateState);

        this.setContentView(R.layout.dishes);
        loadOther();
        loadYao();
        loadSoup();
        loadSaute();
        loadPot();
        loadFry();
        loadDrink();
    }

    private void loadOther(){
        LinearLayout linearLayout = findViewById(R.id.LinerLayoutTypeOther);
        constructView(Menu.other,linearLayout);
    }

    private void loadYao(){
        LinearLayout linearLayout = findViewById(R.id.LinerLayoutTypeYao);
        constructView(Menu.yao,linearLayout);
    }
    private void loadSoup(){
        LinearLayout linearLayout = findViewById(R.id.LinerLayoutTypeSoup);
        constructView(Menu.soup,linearLayout);
    }
    private void loadSaute(){
        LinearLayout linearLayout = findViewById(R.id.LinerLayoutTypeSaute);
        constructView(Menu.saute,linearLayout);
    }
    private void loadPot(){
        LinearLayout linearLayout = findViewById(R.id.LinerLayoutTypePot);
        constructView(Menu.pot,linearLayout);
    }
    private void loadFry(){
        LinearLayout linearLayout = findViewById(R.id.LinerLayoutTypeFry);
        constructView(Menu.fry,linearLayout);
    }
    private void loadDrink(){
        LinearLayout linearLayout = findViewById(R.id.LinerLayoutTypeDrink);
        constructView(Menu.drink,linearLayout);
    }

    private void constructView(HashMap<Long,Dish> dishMap, LinearLayout linearLayout){
        int size = dishMap.size();
        Log.d("加载" + String.valueOf(linearLayout.getId()),String.valueOf(size));

        for (Map.Entry<Long, Dish> entry : dishMap.entrySet()){
            Dish dish = entry.getValue();
            LinearLayout linearLayoutCard = new LinearLayout(this);
            linearLayoutCard.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams linearLayoutCardParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayoutCardParams.setMargins(autoDp(10),autoDp(10),autoDp(10),autoDp(10));
            linearLayoutCard.setLayoutParams(linearLayoutCardParams);

            CardView cardView = new CardView(this);
            CardView.LayoutParams cardViewParams = new CardView.LayoutParams( autoDp(160), autoDp(90));
            cardView.setLayoutParams(cardViewParams);
            cardViewParams.setMargins(0,0,0,autoDp(10));

            cardView.setRadius(autoDp(10));
            ImageView imageView = new ImageView(this);

            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            imageView.setImageBitmap(dish.getImageInBitmap());
            //imageView.setImageResource(R.drawable.logo);

            cardView.addView(imageView);

            TextView textView = new TextView(this);
            textView.setText(dish.getName());
            textView.setGravity(Gravity.CENTER);

            linearLayoutCard.addView(cardView);
            linearLayoutCard.addView(textView);
            linearLayout.addView(linearLayoutCard);

        }
    }

    private int autoDp(int dp){
        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()));
    }

}
