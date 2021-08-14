package com.example.caketouch.table;

public class Food extends Stuff{
    @Override
    public boolean serve() {
        return false;
    }

    public Food(String name, String unitName, float price, float smallPrice, int count, Long ID, Long dishNo, int tableNo) {
        super(name, unitName, price, smallPrice, count, ID, dishNo, tableNo, false);
    }
}
