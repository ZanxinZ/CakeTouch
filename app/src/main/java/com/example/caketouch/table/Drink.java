package com.example.caketouch.table;

public class Drink extends Stuff{

    @Override
    public boolean serve() {
        return false;
    }

    public Drink(String name, float price, float smallPrice, int count, Long time,Long dishNo,int tableNo) {
        super(name, price, smallPrice, count, time, dishNo, tableNo,true);
    }
}
