package com.example.caketouch.menu;

/**
 * 菜的口味
 */
public enum DishTaste {
    //正常，甜，辣
    NORMAL(1),
    SWEET(2),
    SPICY(3);
    public int code;
    DishTaste(int code){
        this.code = code;
    }
}
