package com.example.caketouch.model;

import android.graphics.Bitmap;

import com.example.caketouch.menu.Dish;

public class Model {
    private String imageName;
    private Bitmap image;
    private Dish dish;

    public Model(String imageName, Bitmap image, Dish dish) {
        this.imageName = imageName;
        this.image = image;
        this.dish = dish;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
