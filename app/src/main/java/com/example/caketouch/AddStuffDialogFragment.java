package com.example.caketouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caketouch.menu.Dish;

@SuppressLint("ValidFragment")
public class AddStuffDialogFragment extends DialogFragment {
    Dish dish;
    Button chooseTableBtn;
    int count = 1;
    float priceChoose = 0;

    public interface NoticeDialogListener {
        void onDialogPositiveClick(Dish dish, boolean isNormal, int count, int tableNo);
    }
    NoticeDialogListener noticeDialogListener;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            noticeDialogListener = (NoticeDialogListener)activity;
        }catch (Exception e){
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }

    @SuppressLint("ValidFragment")
    public AddStuffDialogFragment(Dish dish, Button chooseTableBtn) {
        this.dish = dish;
        this.chooseTableBtn = chooseTableBtn;
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
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
                if (countEditText.getText().toString()==null || countEditText.getText().toString().equals("")){
                    count = -1;
                }else {
                    count = Integer.parseInt(countEditText.getText().toString());
                }

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
        TextView stuffChoosePrice = view.findViewById(R.id.textViewStuffChosenPrice);
        normalChoose.setBackground(getResources().getDrawable(R.drawable.chosen_btn));
        priceChoose = dish.getPrice();//default
        stuffChoosePrice.setText(String.valueOf(priceChoose));

        normalChoose.setOnClickListener(v -> {
            smallChoose.setBackground(getResources().getDrawable(R.drawable.choose_btn));
            normalChoose.setBackground(getResources().getDrawable(R.drawable.chosen_btn));
            priceChoose = dish.getPrice();
            stuffChoosePrice.setText(String.valueOf(priceChoose));
        });

        smallChoose.setOnClickListener(v->{
            smallChoose.setBackground(getResources().getDrawable(R.drawable.chosen_btn));
            normalChoose.setBackground(getResources().getDrawable(R.drawable.choose_btn));
            priceChoose = dish.getSmallPrice();
            stuffChoosePrice.setText(String.valueOf(priceChoose));
        });


        Button cancel = view.findViewById(R.id.buttonOrderStuffCancel);
        cancel.setOnClickListener(v -> {
            dismiss();
        });
        Button confirm = view.findViewById(R.id.buttonOrderStuffConfirm);
        confirm.setOnClickListener(v -> {
            dismiss();
            if (count > 0 && count < 101){
                if (priceChoose == dish.getPrice()){
                    noticeDialogListener.onDialogPositiveClick(dish, true, count, chooseTableBtn.getId());
                }else if(priceChoose == dish.getSmallPrice()){
                    noticeDialogListener.onDialogPositiveClick(dish, false, count, chooseTableBtn.getId());
                }
            }else{
                Toast.makeText(getActivity(), "数量不正确，添加失败。", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout linearLayout = view.findViewById(R.id.order_stuff_panel);
        linearLayout.setOnTouchListener((click, event) -> {
            linearLayout.setFocusable(true);
            linearLayout.setFocusableInTouchMode(true);
            linearLayout.requestFocus();
            InputMethodManager imm = (InputMethodManager)(getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
            imm.hideSoftInputFromWindow(linearLayout.getWindowToken(), 0);
            //curEditText.clearFocus();
            return true;
        });

        return builder.create();
    }
}
