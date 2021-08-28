package com.example.caketouch.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.caketouch.R;
import com.example.caketouch.food_for_serve.FoodOrdered;

@SuppressLint("ValidFragment")
public class ServeFoodDialogFragment extends DialogFragment {
    private FoodOrdered foodOrdered;
    private Activity activity;
    @SuppressLint("ValidFragment")
    public ServeFoodDialogFragment(FoodOrdered foodOrdered, Activity activity){
        this.foodOrdered = foodOrdered;
        this.activity = activity;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.dialog_serve_food, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        return builder.create();
    }
    public void constructView(){

    }

}
