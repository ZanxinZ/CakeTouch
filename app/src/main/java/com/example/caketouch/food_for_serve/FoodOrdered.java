package com.example.caketouch.food_for_serve;

import com.example.caketouch.MainActivity;

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
        Long orderTime = MainActivity.tables.get(tableNo).getOrder().getOrderTime();

        int left = 0;
        int right = tablesOrdered.size()-1;
        int middle = (left + right)/2;
        for (int i = 0; i < size/2 ;i++){
            if (orderTime > MainActivity.tables.get(tablesOrdered.get(middle)).getOrder().getOrderTime()){
                left = middle;
                middle = (left + right)/2;
            }else if (orderTime < MainActivity.tables.get(tablesOrdered.get(middle)).getOrder().getOrderTime()) {
                right = middle;
                middle = (left + right)/2;
            }else{

            }
        }

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
