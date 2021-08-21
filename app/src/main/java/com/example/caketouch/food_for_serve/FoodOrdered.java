package com.example.caketouch.food_for_serve;

import java.util.HashSet;
import java.util.Set;

public class FoodOrdered {
    private String foodName = null;
    private int count = 0;
    private Set<Integer> tablesOrdered = new HashSet<>();               // who has ordered current food

    public FoodOrdered(String foodName, int count, Set<Integer> tablesOrdered) {
        this.foodName = foodName;
        this.count = count;
        this.tablesOrdered = tablesOrdered;
    }

    /**
     *
     * @param no tableNo which has this food
     */
    public void addFoodToTable(int no){
        tablesOrdered.add(no);
    }
    public void removeFoodFromTable(int no){
        tablesOrdered.remove(no);
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

    public Set<Integer> getTablesOrdered() {
        return tablesOrdered;
    }

    public void setTablesOrdered(Set<Integer> tablesOrdered) {
        this.tablesOrdered = tablesOrdered;
    }
}
