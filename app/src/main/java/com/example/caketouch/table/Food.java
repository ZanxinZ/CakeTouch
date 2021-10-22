package com.example.caketouch.table;


import com.example.caketouch.menu.DishType;

public class Food extends Stuff{

    public Food(String name, String unitName, float price, Long ID, Long dishNo, DishType dishType, StuffSize stuffSize) {
        super(name, unitName, price, ID, dishNo, dishType, stuffSize, false);
    }

}
