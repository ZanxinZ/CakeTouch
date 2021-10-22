package com.example.caketouch.food_for_serve;


import com.example.caketouch.table.StuffSize;

import java.util.Map;
import java.util.TreeMap;

public class FoodOrdered {
    private String foodName = null;
    private TreeMap<Long, Integer> tablesOrdered = new TreeMap<>(); // stuffID tableNo// who has ordered current food
    //private Long stuffID;
    private Long dishNo;
    public FoodOrdered(String foodName, Long dishNo) {
        this.foodName = foodName;
        this.dishNo = dishNo;
    }

    /**
     *
     * @param tableNo tableNo which has this food
     */
    public void attachTableToFood(int tableNo, Long stuffId){

        if (tablesOrdered.containsKey(stuffId))return;
        tablesOrdered.put(stuffId, tableNo);
    }
    public void removeTableFromFood(int tableNo){
        tablesOrdered.remove(getStuffID(tableNo));
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }


    public Long getDishNo() {
        return dishNo;
    }

    public void setDishNo(Long dishNo) {
        this.dishNo = dishNo;
    }

    public TreeMap<Long, Integer> getTablesOrdered() {
        return tablesOrdered;
    }

    public void setTablesOrdered(TreeMap<Long, Integer> tablesOrdered) {
        this.tablesOrdered = tablesOrdered;
    }

    public Long getStuffID(int tableNo){
        for (Map.Entry<Long, Integer> foodTables:
             tablesOrdered.entrySet()) {
            if (foodTables.getValue().equals(tableNo)){
                Long key = foodTables.getKey();
                return key;
            }
        }
        return null;
    }
}
