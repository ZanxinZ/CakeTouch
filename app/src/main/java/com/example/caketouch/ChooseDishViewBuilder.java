package com.example.caketouch;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.caketouch.fragment.AddStuffDialogFragment;
import com.example.caketouch.menu.Dish;
import com.example.caketouch.menu.Menu;
import com.example.caketouch.model.DishDatabaseHandler;

import java.util.HashMap;
import java.util.Map;


public class ChooseDishViewBuilder {
    Context context;
    Button chooseTableBtn;
    DishDatabaseHandler dishDatabaseHandler;
    public ChooseDishViewBuilder(Context context, ViewGroup parent, Button chooseTableBtn){
        this.context = context;
        this.chooseTableBtn = chooseTableBtn;
        dishDatabaseHandler = new DishDatabaseHandler(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        parent.removeAllViews();
        View view = inflater.inflate(R.layout.dishes, null);
        parent.addView(view);
        dishDatabaseHandler.loadAllDish();
        loadOther(view);
        loadYao(view);
        loadSoup(view);
        loadSaute(view);
        loadPot(view);
        loadFry(view);
        loadDrink(view);
    }

    private void loadOther(View view){
        LinearLayout linearLayout = view.findViewById(R.id.LinerLayoutTypeOther);
        constructView(Menu.other, linearLayout);
    }

    private void loadYao(View view){
        LinearLayout linearLayout = view.findViewById(R.id.LinerLayoutTypeYao);
        constructView(Menu.yao, linearLayout);
    }
    private void loadSoup(View view){
        LinearLayout linearLayout = view.findViewById(R.id.LinerLayoutTypeSoup);
        constructView(Menu.soup, linearLayout);
    }
    private void loadSaute(View view){
        LinearLayout linearLayout = view.findViewById(R.id.LinerLayoutTypeSaute);
        constructView(Menu.saute, linearLayout);
    }
    private void loadPot(View view){
        LinearLayout linearLayout = view.findViewById(R.id.LinerLayoutTypePot);
        constructView(Menu.pot, linearLayout);
    }
    private void loadFry(View view){
        LinearLayout linearLayout = view.findViewById(R.id.LinerLayoutTypeFry);
        constructView(Menu.fry, linearLayout);
    }
    private void loadDrink(View view){
        LinearLayout linearLayout = view.findViewById(R.id.LinerLayoutTypeDrink);
        constructView(Menu.drink, linearLayout);
    }

    private void constructView(HashMap<Long, Dish> dishMap, LinearLayout linearLayout){
        int size = dishMap.size();
        Log.d("加载" + String.valueOf(linearLayout.getId()),String.valueOf(size));

        for (Map.Entry<Long, Dish> entry : dishMap.entrySet()){
            Dish dish = entry.getValue();
            LinearLayout linearLayoutCard = new LinearLayout(context);
            linearLayoutCard.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams linearLayoutCardParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayoutCardParams.setMargins(autoDp(10),autoDp(10),autoDp(10),autoDp(10));
            linearLayoutCard.setLayoutParams(linearLayoutCardParams);

            CardView cardView = new CardView(context);
            CardView.LayoutParams cardViewParams = new CardView.LayoutParams( autoDp(160), autoDp(90));
            cardView.setLayoutParams(cardViewParams);
            cardViewParams.setMargins(0,0,0,autoDp(10));

            cardView.setRadius(autoDp(10));
            ImageView imageView = new ImageView(context);

            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            imageView.setImageBitmap(dish.getImageInBitmap());
            //imageView.setImageResource(R.drawable.logo);

            cardView.addView(imageView);

            TextView textView = new TextView(context);
            textView.setText(dish.getName());
            textView.setGravity(Gravity.CENTER);

            linearLayoutCard.addView(cardView);
            linearLayoutCard.addView(textView);

            linearLayoutCard.setOnClickListener(v->{
                AddStuffDialogFragment addStuffDialogFragment = new AddStuffDialogFragment(dish, chooseTableBtn);
                addStuffDialogFragment.show(((Activity)context).getFragmentManager(), "AddDishDialogFragment");
            });
            linearLayout.addView(linearLayoutCard);
        }
    }

    private int autoDp(int dp){
        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
    }
}
