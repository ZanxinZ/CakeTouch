package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caketouch.Menu.Dish;
import com.example.caketouch.Menu.DishType;
import com.example.caketouch.model.DishDatabaseHandler;

@SuppressLint("ValidFragment")
public class DishDetailFragment extends DialogFragment {
    public Dish dish;
    private DishDatabaseHandler databaseHandler;

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DishDetailFragment dialog);
        void onDialogNegativeClick(DishDetailFragment dialog);
    }

    DishDetailFragment.NoticeDialogListener noticeDialogListener;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            noticeDialogListener = (DishDetailFragment.NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public DishDetailFragment (String dishName, Context context){
        databaseHandler = new DishDatabaseHandler(context);
        dish = databaseHandler.loadDish(dishName);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        if (dish == null){
            Toast.makeText(getActivity(), "菜色不存在", Toast.LENGTH_SHORT).show();
            return null;
        }
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_dish_detail,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        TextView dishName = view.findViewById(R.id.textView_dish_name);
        dishName.setText(dish.getName());
        TextView dishPrice = view.findViewById(R.id.textView_dish_price);
        dishPrice.setText(String.valueOf(dish.getPrice())+" 元/"+dish.getUnitName());
        TextView dishLowPrice = view.findViewById(R.id.textView_dish_low_price);
        dishLowPrice.setText(String.valueOf(dish.getSmallPrice())+" 元/" + dish.getUnitName());
        TextView dishType = view.findViewById(R.id.textView_dish_type);
        dishType.setText(DishType.getType(dish.getDishType()));

        // Delete Dish
        Button buttonDeleteDish = view.findViewById(R.id.buttonDeleteDish);
        buttonDeleteDish.setOnClickListener(v -> {
            ((ViewGroup)(view.getParent())).removeView(view);
            AlertDialog.Builder deleteDishDialogBuilder = new AlertDialog.Builder(getActivity());
            deleteDishDialogBuilder.setMessage("确认要删除【"+dish.getName() + "】？")
                    .setTitle("提示")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            databaseHandler.deleteDish(dish.getName());
                            noticeDialogListener.onDialogPositiveClick(DishDetailFragment.this);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            deleteDishDialogBuilder.show();
        });

        // Update dish
        Button buttonChangeDish = view.findViewById(R.id.buttonChangeDish);
        buttonChangeDish.setOnClickListener(v -> {

        });

//        builder.setPositiveButton("确认",(dialog,id)->{
//
//        });

        return builder.create();
    }
}
