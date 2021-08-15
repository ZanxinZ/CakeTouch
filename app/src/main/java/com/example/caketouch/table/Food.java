package com.example.caketouch.table;

import com.example.caketouch.Menu.Dish;
import com.example.caketouch.Menu.DishUnit;

import java.util.Date;

public class Food extends Stuff{
    public Food(String name, String unitName, float price, Long ID, Long dishNo, int tableNo) {
        super(name, unitName, price, ID, dishNo, tableNo, false);
    }

}
