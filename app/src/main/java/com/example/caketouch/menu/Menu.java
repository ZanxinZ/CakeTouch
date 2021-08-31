package com.example.caketouch.menu;

import java.util.TreeMap;

public class Menu {
    public static TreeMap<Long,Dish> other = new TreeMap<>();
    public static TreeMap<Long,Dish> yao = new TreeMap<>();
    public static TreeMap<Long,Dish> soup = new TreeMap<>();
    public static TreeMap<Long,Dish> saute = new TreeMap<>();
    public static TreeMap<Long,Dish> pot = new TreeMap<>();
    public static TreeMap<Long,Dish> fry = new TreeMap<>();
    public static TreeMap<Long,Dish> drink = new TreeMap<>();

    public static Dish findDish(Long dishNo){
        if (other.containsKey(dishNo))return other.get(dishNo);
        if (yao.containsKey(dishNo))return  yao.get(dishNo);
        if (soup.containsKey(dishNo))return  soup.get(dishNo);
        if (saute.containsKey(dishNo))return  saute.get(dishNo);
        if (pot.containsKey(dishNo))return  pot.get(dishNo);
        if (fry.containsKey(dishNo))return  fry.get(dishNo);
        if (drink.containsKey(dishNo))return  drink.get(dishNo);
        return null;
    }

}
