package com.example.caketouch.food_for_serve;

import java.util.ArrayList;

public class FoodOrdered {
    private String foodName = null;
    private ArrayList<Integer> tablesOrdered = new ArrayList<>();               // who has ordered current food
    private Long stuffID;
    private Long dishNo;
    public FoodOrdered(String foodName, Long stuffID, Long dishNo) {
        this.foodName = foodName;
        this.stuffID = stuffID;
        this.dishNo = dishNo;
    }

    /**
     *
     * @param tableNo tableNo which has this food
     */
    public void attachTableToFood(int tableNo){
        tablesOrdered.add(tableNo);
    }
    public void removeTableFromFood(int tableNo){
        for(int i =0; i < tablesOrdered.size();i++){
            if (tablesOrdered.get(i) == tableNo){
                tablesOrdered.remove(i);
            }
        }
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

    public Long getStuffID() {
        return stuffID;
    }

    public void setStuffID(Long stuffID) {
        this.stuffID = stuffID;
    }

    public Long getDishNo() {
        return dishNo;
    }

    public void setDishNo(Long dishNo) {
        this.dishNo = dishNo;
    }
}
