package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

@SuppressLint("ValidFragment")
public class AddTableDialogFragment extends DialogFragment {
    public int tableNo = 0;
    public Set<Integer> tableNos;
    public AddTableDialogFragment(Set<Integer> tableNos){
        this.tableNos = tableNos;
    }

    public interface NoticeDialogListener {
        void onDialogPositiveClick(AddTableDialogFragment dialog);
        void onDialogNegativeClick(AddTableDialogFragment dialog);
    }
    NoticeDialogListener noticeDialogListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            noticeDialogListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(MainActivity.sContextReference.get());
        View view = inflater.inflate(R.layout.dialog_add_table, null);
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view);

        EditText table_no_edit_text = view.findViewById(R.id.add_table_no);
        table_no_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tableNoStr = table_no_edit_text.getText().toString();
                if (!tableNoStr.equals("")){
                    tableNo = Integer.parseInt(tableNoStr);
                    if (tableNos.contains(tableNo)){
                        //table has been ordered
                        Toast.makeText(MainActivity.sContextReference.get().getApplicationContext(), tableNo + " 号已有订单，重新选择！", Toast.LENGTH_SHORT).show();
                        tableNo = 0;
                    }
                }




            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        builder
                .setPositiveButton("确认", (dialog, id) -> {
                        // Send the positive button event back to the host activity
                        noticeDialogListener.onDialogPositiveClick(AddTableDialogFragment.this);

                });

        return builder.create();
    }
}
