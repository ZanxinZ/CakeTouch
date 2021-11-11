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
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caketouch.MainActivity;
import com.example.caketouch.R;
import com.example.caketouch.food_for_serve.AllOrdered;
import com.example.caketouch.food_for_serve.TableOrdered;
import com.example.caketouch.model.OrderDataBaseHandler;
import com.example.caketouch.table.Stuff;
import com.example.caketouch.table.StuffSize;
import com.example.caketouch.table.Table;

import java.util.Map;

@SuppressLint("ValidFragment")
public class TableDetailDialogFragment extends DialogFragment {
    private TableOrdered tableOrdered;
    private int tableNo;
    Activity activity;
    Bundle bundle;
    private View view;
    public interface NoticeDialogListener {
        void onFoodServed();
    }
    NoticeDialogListener noticeDialogListener;
    @Override
    public void onAttach(Activity activity){

        super.onAttach(activity);
        this.activity = activity;
        try {
            noticeDialogListener = (TableDetailDialogFragment.NoticeDialogListener) activity;
        }catch (Exception e){
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }
    @SuppressLint("ValidFragment")
    public TableDetailDialogFragment(int tableNo, TableOrdered tableOrdered){
        this.tableOrdered = tableOrdered;
        this.tableNo = tableNo;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        bundle = savedInstanceState;
        LayoutInflater inflater = LayoutInflater.from(activity);
        view = inflater.inflate(R.layout.dialog_table_check,null);
        showTitle(view);
        ImageView close = view.findViewById(R.id.imageViewTableCheckClose);
        close.setOnClickListener(v->{
            dismiss();
            noticeDialogListener.onFoodServed();
        });
        LinearLayout linearLayout = view.findViewById(R.id.table_check_stuffs);
        LinearLayout linearLayoutServed = view.findViewById(R.id.table_check_stuffs_served);
        constructSmallStuffs(linearLayout, linearLayoutServed);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        return builder.create();
    }
    @SuppressLint("SetTextI18n")
    private void showTitle(View view){
        TextView title = view.findViewById(R.id.textViewTableCheckTitle);
        title.setText(tableNo + "号 已点￥" + MainActivity.tables.get(tableNo).getOrder().getTotal());
    }

    @SuppressLint("SetTextI18n")
    public void constructSmallStuffs(LinearLayout linearLayout, LinearLayout linearLayoutServed){
        linearLayout.removeAllViews();
        linearLayoutServed.removeAllViews();
        for (Map.Entry<Long, Stuff> stuffEntry:
                tableOrdered.getStuffs().entrySet()) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            View stuffView = inflater.inflate(R.layout.stuff_small_block, null);
            TextView textView = stuffView.findViewById(R.id.textViewStuffSmallName);
            if (stuffEntry.getValue().getStuffSize() == StuffSize.normal){
                textView.setText(stuffEntry.getValue().getName() );
            }else if(stuffEntry.getValue().getStuffSize() == StuffSize.small){
                textView.setText(stuffEntry.getValue().getName() + "(小份)");
            }
            TextView stuffSmallBlockPrice = stuffView.findViewById(R.id.textViewStuffSmallPrice);
            stuffSmallBlockPrice.setText(stuffEntry.getValue().getPrice() + "元");
            if (stuffEntry.getValue().isServed()){
                ImageView imageView = stuffView.findViewById(R.id.imageViewStuffSmall);
                imageView.setVisibility(ViewGroup.VISIBLE);
                linearLayoutServed.addView(stuffView);
            }else{
                linearLayout.addView(stuffView);
                stuffView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder serveFoodDialog = new AlertDialog.Builder(activity);
                        //dismiss();
                        serveFoodDialog.setTitle("提示")
                                .setMessage("确认删除【" + stuffEntry.getValue().getName() + "】吗？")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Table table = MainActivity.tables.get(tableNo);
                                        //table.getOrder().ordered.get(stuffEntry.getValue().getID()).setServed(true);
                                        table.getOrder().ordered.remove(stuffEntry.getValue().getID());//Ordered remove chosenStuff
                                        AllOrdered.foodOrderedMap.get(stuffEntry.getValue().getDishNo()).removeTableFromFood(tableNo);
                                        tableOrdered.removeStuffFromTable(stuffEntry.getKey());        //remove stuff for show
                                        table.getOrder().subtractTotal(stuffEntry.getValue().getPrice());
                                        //stuffEntry.getValue().setServed(true);//remove food from table.
                                        OrderDataBaseHandler orderDataBaseHandler = new OrderDataBaseHandler(activity);
                                        orderDataBaseHandler.updateTable(table, tableNo);
                                        constructSmallStuffs(linearLayout, linearLayoutServed);
                                        showTitle(view);
                                        Toast.makeText(activity, "已删除", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //constructSmallStuffs(linearLayout, linearLayoutServed);
                                    }
                                });
                        serveFoodDialog.show();
                        return false;
                    }
                });
            }
        }
    }

    @Override
    public void onDismiss( DialogInterface dialog){
        noticeDialogListener.onFoodServed();
    }
}
