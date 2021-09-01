package com.example.caketouch.table;

import com.example.caketouch.menu.Dish;

import java.io.Serializable;

/**
 * 桌子业务
 */
public class Table implements Serializable {
    private Order order = new Order();
    private int tableNo;
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

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }
}
