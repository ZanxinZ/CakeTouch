package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caketouch.Menu.Dish;

@SuppressLint("ValidFragment")
public class AddStuffDialogFragment extends DialogFragment {
    Dish dish;
    Button chooseTableBtn;
    int count = 0;
    float priceChoose = 0;
    @SuppressLint("ValidFragment")
    public AddStuffDialogFragment(Dish dish, Button chooseTableBtn) {
        this.dish = dish;
        this.chooseTableBtn = chooseTableBtn;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (dish == null){
            Toast.makeText(getActivity(), "菜色不存在", Toast.LENGTH_SHORT).show();
            return null;
        }
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_order_stuff,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        ImageView dishImg = view.findViewById(R.id.imageViewStuffImg);
        dishImg.setImageBitmap(dish.getImageInBitmap());
        TextView title = view.findViewById(R.id.textViewStuffTitle);
        title.setText(title.getText().toString() + "  " + chooseTableBtn.getText().toString());
        TextView dishName = view.findViewById(R.id.textViewStuffName);
        dishName.setText(dish.getName());
        EditText countEditText = view.findViewById(R.id.editTextStuffCount);
        countEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (count > 0)
                count = Integer.parseInt(countEditText.getText().toString());
            }
        });
        ImageView subStuff = view.findViewById(R.id.imageViewStuffSub);
        subStuff.setOnClickListener(v->{
            int tempCount = Integer.parseInt(countEditText.getText().toString());
            if (tempCount == 1 ){
                Toast.makeText(getActivity(), "数量不能小于1", Toast.LENGTH_SHORT).show();
                return ;
            }
            countEditText.setText(String.valueOf(--tempCount));
            count = tempCount;
        });
        ImageView addStuff = view.findViewById(R.id.imageViewStuffAdd);
        addStuff.setOnClickListener(v -> {
            int tempCount = Integer.parseInt(countEditText.getText().toString());
            if (tempCount == 100 ){
                Toast.makeText(getActivity(), "数量不能大于100", Toast.LENGTH_SHORT).show();
                return ;
            }
            countEditText.setText(String.valueOf(++tempCount));
            count = tempCount;
        });
        TextView normalChoose = view.findViewById(R.id.textViewStuffNormal);
        TextView smallChoose = view.findViewById(R.id.textViewStuffSmall);

        normalChoose.setBackground(getResources().getDrawable(R.drawable.chosen_btn));
        priceChoose = dish.getPrice();//default

        normalChoose.setOnClickListener(v -> {
            smallChoose.setBackground(getResources().getDrawable(R.drawable.choose_btn));
            normalChoose.setBackground(getResources().getDrawable(R.drawable.chosen_btn));
            priceChoose = dish.getPrice();
        });

        smallChoose.setOnClickListener(v->{
            smallChoose.setBackground(getResources().getDrawable(R.drawable.chosen_btn));
            normalChoose.setBackground(getResources().getDrawable(R.drawable.choose_btn));
            priceChoose = dish.getSmallPrice();
        });


        Button cancel = view.findViewById(R.id.buttonOrderStuffCancel);
        cancel.setOnClickListener(v -> {
            dismiss();
        });
        Button confirm = view.findViewById(R.id.buttonOrderStuffConfirm);
        confirm.setOnClickListener(v -> {

        });
        return builder.create();
    }
}
