package com.example.caketouch.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caketouch.MainActivity;
import com.example.caketouch.R;
import com.example.caketouch.food_for_serve.AllOrdered;
import com.example.caketouch.food_for_serve.FoodOrdered;

@SuppressLint("ValidFragment")
public class ServeFoodDialogFragment extends DialogFragment {
    private FoodOrdered foodOrdered;
    private Activity activity;
    private LinearLayout curRow;
    private int chosenTableNo = -1;
    private View chosenTableTextView = null;
    private int smallTableCount = 0;
    @SuppressLint("ValidFragment")
    public ServeFoodDialogFragment(FoodOrdered foodOrdered, Activity activity){
        this.foodOrdered = foodOrdered;
        this.activity = activity;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.dialog_serve_food, null);
        ImageView close = view.findViewById(R.id.imageViewServeFoodClose);
        close.setOnClickListener(v->{
            dismiss();
        });
        TextView title = view.findViewById(R.id.textViewTableCheckTitle);
        title.setText(title.getText() + foodOrdered.getFoodName() + "】");
        
        LinearLayout linearLayout = view.findViewById(R.id.table_small_blocks);

        for (Integer tableNo:
             foodOrdered.getTablesOrdered()) {
            addSmallTable(linearLayout,tableNo);
        }
        Button confirm = view.findViewById(R.id.buttonServeFoodConfirm);
        confirm.setOnClickListener(v->{
            if (chosenTableNo != -1){
                MainActivity.tables.get(chosenTableNo).getOrder().serveStuff(foodOrdered.getStuffID());
                AllOrdered.foodOrderedMap.get(foodOrdered.getDishNo()).removeTableFromFood(chosenTableNo);
                AllOrdered.tableOrderedMap.get((long)chosenTableNo).getStuffs().remove(foodOrdered.getStuffID());
                //刷新activity
                Toast.makeText(activity, "Served", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        return builder.create();
    }
    @SuppressLint("SetTextI18n")
    public void addSmallTable(LinearLayout linearLayout, int tableNo){
        if (smallTableCount % 4 == 0){
            curRow = new LinearLayout(activity);
            linearLayout.addView(curRow);
        }
        LayoutInflater inflater = LayoutInflater.from(activity);
        View table_small_block = inflater.inflate(R.layout.table_small_block, null);
        TextView textView = table_small_block.findViewById(R.id.textViewTableSmallBlockTableNo);
        textView.setText(String.valueOf(tableNo)+ " "+ textView.getText());
        textView.setOnClickListener(v->{
            Toast.makeText(activity, String.valueOf(tableNo), Toast.LENGTH_SHORT).show();
            if (chosenTableNo == -1){
                // the first one be click
                chosenTableNo = tableNo;
                chosenTableTextView = textView;
                textView.setBackground(getResources().getDrawable(R.drawable.table_small_block_select));
            }else{
                // overlap the new click one to chosenTableTextView
                chosenTableNo = tableNo;
                chosenTableTextView.setBackground(getResources().getDrawable(R.drawable.table_small_block));
                textView.setBackground(getResources().getDrawable(R.drawable.table_small_block_select));
                chosenTableTextView = textView;
            }
        });


        curRow.addView(table_small_block);
        smallTableCount++;
    }

}
