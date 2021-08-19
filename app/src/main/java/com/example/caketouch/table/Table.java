package com.example.caketouch.table;

import android.view.View;
import android.widget.Button;

import com.example.caketouch.Menu.Dish;

/**
 * 桌子业务
 */
public class Table {
    private Order order = new Order();
    private Button button;
    private int people = 0;
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void orderStuff(Dish dish, boolean isNormal, int count, int tableNo){
        order.orderStuff(dish, isNormal, count, tableNo);
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
