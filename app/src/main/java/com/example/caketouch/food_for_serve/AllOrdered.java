package com.example.caketouch.food_for_serve;


import java.util.HashMap;


public class AllOrdered {
    // public static HashMap<Long, Integer> dishRecord = new HashMap<>(); // dishNo, count
    public static HashMap<Long, FoodOrdered> foodOrderedMap = new HashMap<>();//dishNo, foodOrdered



    public static HashMap<Long, FoodOrdered> getFoodOrderedMap() {
        return foodOrderedMap;
    }

    public static void setFoodOrderedMap(HashMap<Long, FoodOrdered> foodOrderedMap) {
        AllOrdered.foodOrderedMap = foodOrderedMap;
    }
}
