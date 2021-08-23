package com.example.caketouch.table;

import android.widget.Button;

import com.example.caketouch.menu.Dish;

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

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public void orderStuff(Dish dish, boolean isNormal, int count){
        order.orderStuff(dish, isNormal, count);
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
