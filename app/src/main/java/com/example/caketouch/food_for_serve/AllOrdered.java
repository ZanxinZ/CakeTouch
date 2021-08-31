package com.example.caketouch.food_for_serve;


import java.util.HashMap;
import java.util.TreeMap;


/**
 * OrderedShowDetail
 */
public class AllOrdered {
    // public static HashMap<Long, Integer> dishRecord = new HashMap<>(); // dishNo, count
    public static TreeMap<Long, FoodOrdered> foodOrderedMap = new TreeMap<>();//dishNo, foodOrdered
    public static HashMap<Integer, TableOrdered> tableOrderedMap = new HashMap<>();//tableNo, tableOrdered

}
