package com.example.caketouch.food_for_serve;

import java.util.ArrayList;

public class FoodOrdered {
    private String foodName = null;
    private ArrayList<Integer> tablesOrdered = new ArrayList<>();               // who has ordered current food

    public FoodOrdered(String foodName) {
        this.foodName = foodName;
    }

    /**
     *
     * @param tableNo tableNo which has this food
     */
    public void attachTableToFood(int tableNo){
        tablesOrdered.add(tableNo);
    }
    public void removeTableFromFood(int tableNo){
        tablesOrdered.remove(tableNo);
    }


    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }


    public ArrayList<Integer> getTablesOrdered() {
        return tablesOrdered;
    }

    public void setTablesOrdered(ArrayList<Integer> tablesOrdered) {
        this.tablesOrdered = tablesOrdered;
    }
}
