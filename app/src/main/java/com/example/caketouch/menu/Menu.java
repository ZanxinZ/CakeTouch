package com.example.caketouch.menu;


import java.util.HashMap;

public class Menu {
    public static HashMap<Long,Dish> other = new HashMap<>();
    public static HashMap<Long,Dish> yao = new HashMap<>();
    public static HashMap<Long,Dish> soup = new HashMap<>();
    public static HashMap<Long,Dish> saute = new HashMap<>();
    public static HashMap<Long,Dish> pot = new HashMap<>();
    public static HashMap<Long,Dish> fry = new HashMap<>();
    public static HashMap<Long,Dish> drink = new HashMap<>();

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
