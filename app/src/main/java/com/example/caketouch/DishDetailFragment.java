package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.example.caketouch.Menu.Dish;

@SuppressLint("ValidFragment")
public class DishDetailFragment extends DialogFragment {
    private Dish dish;
    public DishDetailFragment (Dish dish){
        this.dish = dish;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater inflater = LayoutInflater.from(DishShowActivity.context.get());
        View view = inflater.inflate(R.layout.dialog_dish_detail,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }
}
