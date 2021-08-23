package com.example.caketouch.food_for_serve;


import java.util.HashMap;


/**
 * OrderedShowDetail
 */
public class AllOrdered {
    // public static HashMap<Long, Integer> dishRecord = new HashMap<>(); // dishNo, count
    public static HashMap<Long, FoodOrdered> foodOrderedMap = new HashMap<>();//dishNo, foodOrdered
    public static HashMap<Long, TableOrdered> tableOrderedMap = new HashMap<>();//tableNo, tableOrdered

    public static HashMap<Long, FoodOrdered> getFoodOrderedMap() {
        return foodOrderedMap;
    }

    public static HashMap<Long, TableOrdered> getTableOrderedMap() {
        return tableOrderedMap;
    }

}
