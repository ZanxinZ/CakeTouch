package com.example.caketouch.table;

import android.graphics.Bitmap;

public class Food extends Stuff{
    private Bitmap foodImage;


    public Food(String name, String unitName, float price, Long ID, Long dishNo) {
        super(name, unitName, price, ID, dishNo, false);
    }

    public Food(String name, String unitName, float price, Long ID, Long dishNo, Bitmap foodImage) {
        super(name, unitName, price, ID, dishNo, false);
        this.foodImage = foodImage;
    }

    public Food(Stuff stuff, Bitmap bitmap){
        super(stuff.name, stuff.unitName, stuff.price, stuff.ID, stuff.dishNo, stuff.served);
        this.foodImage = bitmap;

    }

    public Bitmap getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(Bitmap foodImage) {
        this.foodImage = foodImage;
    }
}
