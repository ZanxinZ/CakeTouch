package com.example.caketouch.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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

import java.util.Map;
import java.util.Objects;

@SuppressLint("ValidFragment")
public class ServeFoodDialogFragment extends DialogFragment {
    private FoodOrdered foodOrdered;
    private Activity activity;
    private LinearLayout curRow;
    private int chosenTableNo = -1;
    private View chosenTableTextView = null;
    private int smallTableCount = 0;

    public interface NoticeDialogListener{
        void onFoodServed();
    }
    ServeFoodDialogFragment.NoticeDialogListener noticeDialogListener;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
        try{
            noticeDialogListener = (ServeFoodDialogFragment.NoticeDialogListener)activity;
        }catch (Exception e){
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }

    @SuppressLint("ValidFragment")
    public ServeFoodDialogFragment(FoodOrdered foodOrdered){
        this.foodOrdered = foodOrdered;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.dialog_serve_food, null);
        ImageView close = view.findViewById(R.id.imageViewServeFoodClose);
        close.setOnClickListener(v->{
            dismiss();
            noticeDialogListener.onFoodServed();
        });
        TextView title = view.findViewById(R.id.textViewTableCheckTitle);
        title.setText(title.getText() + foodOrdered.getFoodName() + "】");
        
        LinearLayout linearLayout = view.findViewById(R.id.table_small_blocks);

        for (Map.Entry<Long, Integer> tableFoodMap:
             foodOrdered.getTablesOrdered().entrySet()) {
            addSmallTable(linearLayout,tableFoodMap.getValue());
        }
        Button confirm = view.findViewById(R.id.buttonServeFoodConfirm);
        confirm.setOnClickListener(v->{
            if (chosenTableNo != -1){
                Long stuffID = foodOrdered.getStuffID(chosenTableNo);// TreeMap 访问有异步性质
                MainActivity.tables.get(chosenTableNo).getOrder().serveStuff(stuffID);
                Objects.requireNonNull(AllOrdered.foodOrderedMap.get(foodOrdered.getDishNo())).removeTableFromFood(chosenTableNo);
                Objects.requireNonNull(AllOrdered.tableOrderedMap.get(chosenTableNo)).removeStuffFromTable(stuffID);
                //刷新activity
                Toast.makeText(activity, "已上菜", Toast.LENGTH_SHORT).show();
            }
            dismiss();
            noticeDialogListener.onFoodServed();
        });
        Button cancel = view.findViewById(R.id.buttonServeFoodCancel);
        cancel.setOnClickListener(v->{
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
    @Override
    public void onDismiss( DialogInterface dialog){
        noticeDialogListener.onFoodServed();
    }

}
