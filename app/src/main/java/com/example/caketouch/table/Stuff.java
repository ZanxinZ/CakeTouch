package com.example.caketouch.table;

import java.util.Date;

/**
 * 物品
 */
public abstract class Stuff {
    protected String name;
    protected float price;//大份价格
    protected float smallPrice;//小份价格
    protected int count;
    protected Long time;
    protected boolean served;
    protected Long dishNo;
    protected int tableNo;
    public Stuff(String name, float price, float smallPrice, int count, Long time,  Long dishNo, int tableNo, boolean served) {
        this.name = name;
        this.price = price;
        this.smallPrice = smallPrice;
        this.count = count;
        this.time = time;
        this.dishNo = dishNo;
        this.tableNo = tableNo;
        this.served = served;
    }

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

    public boolean isServed() {
        return served;
    }

    public void setServed(boolean served) {
        this.served = served;
    }

    public Long getDishNo() {
        return dishNo;
    }

    public void setDishNo(Long dishNo) {
        this.dishNo = dishNo;
    }

    public abstract boolean serve();
}
