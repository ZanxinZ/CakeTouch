package com.example.caketouch.table;

public class DishInfo {
    private String name;
    private float price;
    private float smallPrice;
    private String picPath;
    private FoodType foodType;

    public DishInfo(String name, float price, float smallPrice, String picPath, FoodType foodType) {
        this.name = name;
        this.price = price;
        this.smallPrice = smallPrice;
        this.picPath = picPath;
        this.foodType = foodType;
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

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }
}
