package com.example.caketouch.food_for_serve;


import java.io.Serializable;

public class DrinkOrdered implements Serializable {
    private Long dishNo;
    private int count;
    private String name;
    private String dishUnit;
    private float money;
    public DrinkOrdered(Long dishNo, int count, String name, String dishUnit, float money) {
        this.dishNo = dishNo;
        this.count = count;
        this.name = name;
        this.dishUnit = dishUnit;
        this.money = money;
    }

    public Long getDishNo() {
        return dishNo;
    }

    public void setDishNo(Long dishNo) {
        this.dishNo = dishNo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDishUnit() {
        return dishUnit;
    }

    public void setDishUnit(String dishUnit) {
        this.dishUnit = dishUnit;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
