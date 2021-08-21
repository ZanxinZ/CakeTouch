package com.example.caketouch.food_for_serve;

import java.util.ArrayList;

public class FoodOrdered {
    private String foodName = null;
    private int count = 0;
    private ArrayList<Integer> tablesOrdered = new ArrayList<>();               // who has ordered current food

    public FoodOrdered(String foodName, int count, ArrayList<Integer> tablesOrdered) {
        this.foodName = foodName;
        this.count = count;
        this.tablesOrdered = tablesOrdered;
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

    public void addCount(int count){
        this.count = this.count + count;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Integer> getTablesOrdered() {
        return tablesOrdered;
    }

    public void setTablesOrdered(ArrayList<Integer> tablesOrdered) {
        this.tablesOrdered = tablesOrdered;
    }
}
