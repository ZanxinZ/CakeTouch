package com.example.caketouch.table;

import java.util.Date;

public abstract class Stuff {
    protected String name;
    protected float price;//大份价格
    protected float smallPrice;//小份价格
    protected int count;
    protected Long time;

    public Stuff(){
        this.time = new Date().getTime();
        this.count = 0;
        this.price = -1;
        this.name = "空";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getSmallPrice() {
        return smallPrice;
    }

    public void setSmallPrice(float smallPrice) {
        this.smallPrice = smallPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getTime() {
        return time;
    }

    public abstract boolean serve();
}
