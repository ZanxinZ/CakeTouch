package com.example.caketouch.table;

import android.graphics.Bitmap;

public class Food extends Stuff{
    private Bitmap foodImage;


    public Food(String name, String unitName, float price, Long ID, Long dishNo, int tableNo) {
        super(name, unitName, price, ID, dishNo, tableNo, false);
    }

    public Food(String name, String unitName, float price, Long ID, Long dishNo, int tableNo, Bitmap foodImage) {
        super(name, unitName, price, ID, dishNo, tableNo, false);
        this.foodImage = foodImage;
    }

    public Bitmap getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(Bitmap foodImage) {
        this.foodImage = foodImage;
    }
}
