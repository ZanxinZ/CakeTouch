package com.example.caketouch.table;

public class Food extends Stuff{
    @Override
    public boolean serve() {
        return false;
    }

    public Food(String name, float price, float smallPrice, int count, Long time, Long dishNo, int tableNo) {
        super(name, price, smallPrice, count, time, dishNo, tableNo, false);
    }
}
