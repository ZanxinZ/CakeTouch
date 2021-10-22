package com.example.caketouch.table;

import com.example.caketouch.menu.DishType;

import java.io.Serializable;
import java.util.Date;

/**
 * 物品
 */
public abstract class Stuff implements Serializable {
    protected String name;      //名称
    protected String unitName;  //单位名称
    protected float price;      //价格
    protected StuffSize stuffSize;
    protected Long ID;          //点菜时间
    protected boolean served;   //是否已交付
    protected Long dishNo;      //菜色编号
    //protected int tableNo;      //桌号
    protected DishType dishType;
    public Stuff(String name, String unitName, float price, Long ID,  Long dishNo, DishType dishType, StuffSize stuffSize,boolean served) {
        this.name = name;
        this.unitName = unitName;
        this.price = price;
        this.stuffSize = stuffSize;
        this.ID = ID;
        this.dishNo = dishNo;
        //this.tableNo = tableNo;
        this.served = served;
        this.dishType = dishType;
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

    public Long getID() {
        return ID;
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

//    public abstract boolean serve(Stuff stuff);
//
//    public abstract boolean order(Dish dish, int count, int tableNo);

    public DishType getDishType() {
        return dishType;
    }

    public String getUnitName() {
        return unitName;
    }

    public StuffSize getStuffSize() {
        return stuffSize;
    }
}
