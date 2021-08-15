package com.example.caketouch.table;

import com.example.caketouch.Menu.Dish;
import com.example.caketouch.Menu.DishUnit;

import java.util.Date;

public class Drink extends Stuff{


    public Drink(String name, String unitName, float price, Long ID, Long dishNo,int tableNo) {
        super(name, unitName, price, ID, dishNo, tableNo,true);
    }
}
