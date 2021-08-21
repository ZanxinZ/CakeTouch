package com.example.caketouch.food_for_serve;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AllOrdered {
    public static HashMap<Long, Integer> dishRecord = new HashMap<>(); // dishNo, count


    public static HashMap<Long, Integer> getDishRecord() {
        return dishRecord;
    }

    public static void setDishRecord(HashMap<Long, Integer> dishRecord) {
        AllOrdered.dishRecord = dishRecord;
    }


}
