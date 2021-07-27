package com.example.caketouch.table;

import android.view.View;
import android.widget.Button;

/**
 * 桌子业务
 */
public class Table {
    private Order order;
    private Button button;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
