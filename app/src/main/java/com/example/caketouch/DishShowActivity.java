package com.example.caketouch;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caketouch.fragment.DishDetailFragment;
import com.example.caketouch.menu.Dish;
import com.example.caketouch.menu.Menu;
import com.example.caketouch.model.DishDatabaseHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DishShowActivity extends Activity implements DishDetailFragment.NoticeDialogListener{
    //public static WeakReference<Context> context;
    DishDatabaseHandler databaseHandler;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstateState){
        super.onCreate(savedInstateState);
        databaseHandler = new DishDatabaseHandler(this.getBaseContext());
        //context = new WeakReference<Context>(this);
        databaseHandler.loadAllDish();
        this.setContentView(R.layout.activity_dish_show);

        loadOther();
        loadYao();
        loadSoup();
        loadSaute();
        loadPot();
        loadFry();
        loadDrink();
        ImageView imageView = findViewById(R.id.imageViewDishShowBack);
        imageView.setOnClickListener(v->{
            finish();
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadOther(){
        LinearLayout linearLayout = findViewById(R.id.LinerLayoutTypeOther);
        constructView(Menu.other,linearLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadYao(){
        LinearLayout linearLayout = findViewById(R.id.LinerLayoutTypeYao);
        constructView(Menu.yao,linearLayout);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadSoup(){
        LinearLayout linearLayout = findViewById(R.id.LinerLayoutTypeSoup);
        constructView(Menu.soup,linearLayout);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadSaute(){
        LinearLayout linearLayout = findViewById(R.id.LinerLayoutTypeSaute);
        constructView(Menu.saute,linearLayout);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadPot(){
        LinearLayout linearLayout = findViewById(R.id.LinerLayoutTypePot);
        constructView(Menu.pot,linearLayout);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadFry(){
        LinearLayout linearLayout = findViewById(R.id.LinerLayoutTypeFry);
        constructView(Menu.fry,linearLayout);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadDrink(){
        LinearLayout linearLayout = findViewById(R.id.LinerLayoutTypeDrink);
        constructView(Menu.drink,linearLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void constructView(TreeMap<Long,Dish> dishMap, LinearLayout linearLayout){
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

            linearLayoutCard.setOnClickListener(v->{
                DishDetailFragment dishDetailFragment = new DishDetailFragment(dish.getDishNo(), DishShowActivity.this);
                dishDetailFragment.show(DishShowActivity.this.getFragmentManager(), "DishDetailFragment");
            });
            linearLayout.addView(linearLayoutCard);

        }
    }

    private int autoDp(int dp){
        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onDialogPositiveClick(DishDetailFragment dialog) {
        Toast.makeText(this, "更新", Toast.LENGTH_SHORT).show();
        //this.recreate();
        finish();
    }



    @Override
    public void onDialogNegativeClick(DishDetailFragment dialog) {
        
    }
}
