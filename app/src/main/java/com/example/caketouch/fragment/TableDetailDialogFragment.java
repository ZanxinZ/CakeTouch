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
import com.example.caketouch.food_for_serve.TableOrdered;

@SuppressLint("ValidFragment")
public class TableDetailDialogFragment extends DialogFragment {
    private TableOrdered tableOrdered;
    Activity activity;
    public interface NoticeDialogListener {
        void onTableDialogPositiveClick(DishDetailFragment dialog);
        void onTableDialogNegativeClick(DishDetailFragment dialog);
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
    public TableDetailDialogFragment(TableOrdered tableOrdered){
        this.tableOrdered = tableOrdered;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.dialog_table_check,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        return builder.create();
    }
}
