package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caketouch.Menu.Dish;
import com.example.caketouch.Menu.DishType;
import com.example.caketouch.Menu.DishUnit;
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
    public DishDetailFragment (Long dishNo, Context context){
        databaseHandler = new DishDatabaseHandler(context);
        dish = databaseHandler.loadDish(dishNo);
    }


    @SuppressLint("SetTextI18n")
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

        ImageView dishImg = view.findViewById(R.id.imageViewDishBitmap);
        dishImg.setImageBitmap(dish.getImageInBitmap());
        TextView dishName = view.findViewById(R.id.textView_dish_name);
        dishName.setText(dish.getName());
        TextView dishPrice = view.findViewById(R.id.textView_dish_price);
        dishPrice.setText(String.valueOf(dish.getPrice())+" 元/" + DishUnit.getUnitStr(dish.getUnit()));
        TextView dishLowPrice = view.findViewById(R.id.textView_dish_low_price);
        dishLowPrice.setText(String.valueOf(dish.getSmallPrice())+" 元/" + DishUnit.getUnitStr(dish.getUnit()));
        TextView dishType = view.findViewById(R.id.textView_dish_type);
        dishType.setText(DishType.getType(dish.getDishType()));

        // Delete Dish
        Button buttonDeleteDish = view.findViewById(R.id.buttonDeleteDish);
        buttonDeleteDish.setOnClickListener(v -> {
            //((ViewGroup)(view.getParent())).removeView(view);
            dismiss();
            AlertDialog.Builder deleteDishDialogBuilder = new AlertDialog.Builder(getActivity());
            deleteDishDialogBuilder.setMessage("确认要删除【"+dish.getName() + "】？")
                    .setTitle("提示")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            databaseHandler.deleteDish(dish.getDishNo());
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
            ((ViewGroup) view).removeAllViews();
            LayoutInflater dishUpdateInflater = LayoutInflater.from(getActivity());
            View dishUpdateView = dishUpdateInflater.inflate(R.layout.dialog_dish_detail_update, null);
            ((ViewGroup) (view.getParent())).addView(dishUpdateView);

            ImageView imageView = dishUpdateView.findViewById(R.id.imageViewDishUpdate);
            imageView.setImageBitmap(dish.getImageInBitmap());
            EditText dishNameUpdate = dishUpdateView.findViewById(R.id.textView_dish_name_update);
            dishNameUpdate.setText(dish.getName());
            EditText dishPriceUpdate = dishUpdateView.findViewById(R.id.textView_dish_price_update);
            dishPriceUpdate.setText(String.valueOf(dish.getPrice()));
            EditText dishLowPriceUpdate = dishUpdateView.findViewById(R.id.textView_dish_low_price_update);
            dishLowPriceUpdate.setText(String.valueOf(dish.getSmallPrice()));
            Spinner dishUnitUpdate = dishUpdateView.findViewById(R.id.spinnerDishUnitUpdate);
            dishUnitUpdate.setSelection(dish.getUnit().code);
            Spinner dishTypeUpdate = dishUpdateView.findViewById(R.id.spinnerDishTypeUpdate);
            dishTypeUpdate.setSelection(dish.getDishType().code);

            Button updateCancel = dishUpdateView.findViewById(R.id.buttonDishCancelDishUpdate);
            updateCancel.setOnClickListener(action->{
                dismiss();
            });
            Button updateConfirm = dishUpdateView.findViewById(R.id.buttonConfirmDishUpdate);
            updateConfirm.setOnClickListener(action -> {
                Dish dishForUpdate = new Dish(dishNameUpdate.getText().toString(),
                        DishUnit.getDishUnit(dishUnitUpdate.getSelectedItem().toString()),
                        ((BitmapDrawable)(imageView.getDrawable())).getBitmap(),
                        Float.parseFloat(dishPriceUpdate.getText().toString()),
                        Float.parseFloat(dishLowPriceUpdate.getText().toString()),
                        DishType.getDishType(dishTypeUpdate.getSelectedItem().toString()),
                        dish.getDishNo());
                Log.d("更新",dishForUpdate.getDishNo().toString());
                if (databaseHandler.updateDish(dishForUpdate)){
                    dismiss();
                    getActivity().finish();
                }
            });

        });

        
        Button buttonConfirm = view.findViewById(R.id.buttonConfirmDishDetail);
        buttonConfirm.setOnClickListener(v->{
            dismiss();
        });


        return builder.create();
    }
}
