package com.example.caketouch.table;

public class Drink extends Stuff{
    private int count = 0;

    public Drink(String name, String unitName, float price, Long ID, Long dishNo) {
        super(name, unitName, price, ID, dishNo,true);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
