package com.example.caketouch.Menu;

import android.graphics.Bitmap;

/**
 * 菜色
 */
public class Dish {
    private String name;
    private String unitName;       //单位
    private Bitmap image;
    private float price;
    private float smallPrice;      //小份价格
    private DishType dishType;
    private Long dishNo;

    public Dish(String name, String unitName, Bitmap image, float price,  float smallPrice, DishType dishType, Long dishNo) {
        this.name = name;
        this.unitName = unitName;
        this.image = image;
        this.price = price;
        this.smallPrice = smallPrice;
        this.dishType = dishType;
        this.dishNo = dishNo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
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

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }

    public Long getDishNo() {
        return dishNo;
    }

    public void setDishNo(Long dishNo) {
        this.dishNo = dishNo;
    }


}
