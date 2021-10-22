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
import com.example.caketouch.model.OrderDataBaseHandler;
import com.example.caketouch.table.StuffSize;

import java.util.Map;
import java.util.Objects;

/**
 * to show which table ordered food
 */
@SuppressLint("ValidFragment")
public class ServeFoodDialogFragment extends DialogFragment {
    private FoodOrdered foodOrdered;
    private Activity activity;
    private LinearLayout curRow;
    private int chosenTableNo = -1;
    private View chosenTableTextView = null;
    private int smallTableCount = 0;

    public interface NoticeDialogListener{
        void onFoodServed();//refresh parent UI
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

        for (Map.Entry<Long, Integer> tablesHaveFood:
             foodOrdered.getTablesOrdered().entrySet()) {
            addSmallTable(linearLayout, tablesHaveFood.getValue(), Objects.requireNonNull(MainActivity.tables.get(tablesHaveFood.getValue()).getOrder().ordered.get(tablesHaveFood.getKey())).getStuffSize());
        }
        Button confirm = view.findViewById(R.id.buttonServeFoodConfirm);
        confirm.setOnClickListener(v->{
            if (chosenTableNo != -1){
                Long stuffID = foodOrdered.getStuffID(chosenTableNo);// TreeMap 访问有异步性质
                MainActivity.tables.get(chosenTableNo).getOrder().serveStuff(stuffID);
                Objects.requireNonNull(AllOrdered.foodOrderedMap.get(foodOrdered.getDishNo())).removeTableFromFood(chosenTableNo);
                Objects.requireNonNull(AllOrdered.tableOrderedMap.get(chosenTableNo)).removeStuffFromTable(stuffID);
                OrderDataBaseHandler orderDataBaseHandler = new OrderDataBaseHandler(activity);
                orderDataBaseHandler.updateTable(MainActivity.tables.get(chosenTableNo), chosenTableNo);
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
    public void addSmallTable(LinearLayout linearLayout, int tableNo, StuffSize stuffSize){
        if (smallTableCount % 4 == 0){
            curRow = new LinearLayout(activity);
            linearLayout.addView(curRow);
        }
        LayoutInflater inflater = LayoutInflater.from(activity);
        View table_small_block = inflater.inflate(R.layout.table_small_block, null);
        TextView textView = table_small_block.findViewById(R.id.textViewTableSmallBlockTableNo);
        if (stuffSize == StuffSize.normal){
            textView.setText(tableNo +""+ textView.getText());
        }else if(stuffSize == StuffSize.small){
            textView.setText(tableNo +""+ textView.getText() + "(小份)");
        }

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
