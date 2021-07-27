package com.example.caketouch.Menu;

/**
 * 菜色
 */
public class Dish {
    private String name;
    private float price;
    private float smallPrice;      //小份价格
    private String picPath;        //图片路径
    private DishType dishType;
    private Long dishNo;

    private Dish(String name, float price, float smallPrice, String picPath, DishType dishType, Long dishNo) {
        this.name = name;
        this.price = price;
        this.smallPrice = smallPrice;
        this.picPath = picPath;
        this.dishType = dishType;
        this.dishNo = dishNo;
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

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }

    public Long getdishNo() {
        return dishNo;
    }

    public void setdishNo(Long dishNo) {
        this.dishNo = dishNo;
    }
}
