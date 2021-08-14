package com.example.caketouch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class OrderViewBuilder {
    public OrderViewBuilder(Context context, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dishes, null);
        parent.addView(view);
    }

    private void constructView(){

    }
}
