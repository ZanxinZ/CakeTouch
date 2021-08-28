package com.example.caketouch.table;


import android.util.Log;

import com.example.caketouch.food_for_serve.AllOrdered;
import com.example.caketouch.food_for_serve.DrinkOrdered;
import com.example.caketouch.food_for_serve.FoodOrdered;
import com.example.caketouch.menu.Dish;
import com.example.caketouch.menu.DishType;
import com.example.caketouch.menu.DishUnit;
import com.example.caketouch.util.DateGet;

import java.util.Date;
import java.util.HashMap;

/**
 * 记录点的每一个物品，每个物品有一个ID(time)，相同的菜色有相同的 DishNo
 */
public class Order {
    //记录点的每个物品
    public HashMap<Long, Stuff> ordered = new HashMap<>();   // stuffId, stuff
    public HashMap<Long, DrinkOrdered> drinkOrderedMap = new HashMap<>(); // DishNo, drinkOrdered

    private static Long orderTime;//点餐时间
    private static float total;     //总价
    public Order() {
        Order.orderTime = new Date().getTime();
    }

    public Stuff orderStuff(Dish dish, boolean isNormal, int count){
        if (count < 1 || count > 101)return null;
        Stuff stuff = null;

        if (dish.getDishType() == DishType.drink){

            Drink drink;
            if (drinkOrdered.containsKey(dish.getDishNo())){
                drink = drinkOrdered.get(dish.getDishNo());
            }else{
                if (isNormal){
                    drink = new Drink(dish.getName(), DishUnit.getUnitStr(dish.getUnit()),
                            dish.getPrice(), DateGet.Time(), dish.getDishNo());

                }else {
                    drink = new Drink(dish.getName(), DishUnit.getUnitStr(dish.getUnit()),
                            dish.getSmallPrice(), DateGet.Time(), dish.getDishNo());
                }
            }

            assert drink != null;
            drink.setCount(drink.getCount() + count);
            drinkOrdered.put(drink.getDishNo(), drink);
            // Because use the mills as the ID, it can't be duplicate.
            // if continuously put to hashmap, it's so fast that the mills time number will be the same, so wait for 1 millisecond
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stuff = drink;


        }
        else{
            for (int i = 0; i < count; i++) {
                if (isNormal){
                    stuff = new Food(dish.getName(), DishUnit.getUnitStr(dish.getUnit()),
                            dish.getPrice(), DateGet.Time(), dish.getDishNo());
                }else {
                    stuff = new Food(dish.getName(), DishUnit.getUnitStr(dish.getUnit()),
                            dish.getSmallPrice(), DateGet.Time(), dish.getDishNo());
                }
                ordered.put(stuff.getID(), stuff);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

//        addToDishRecord(stuff.getDishNo(), count);
        return stuff;
    }


    /**
     * 上菜或饮料
     * @param stuffId
     */
    public boolean serveStuff(Long stuffId){
        Stuff stuff = ordered.get(stuffId);
        if (stuff == null)return false;
        stuff.setServed(true);
        return true;
    }

    public Long getOrderTime() {
        return orderTime;
    }

//    public void addToDishRecord(Long dishNo, int count){
//        if (AllOrdered.dishRecord.containsKey(dishNo)){
//            AllOrdered.dishRecord.put(dishNo, AllOrdered.dishRecord.get(dishNo) + count);
//        }else{
//            AllOrdered.dishRecord.put(dishNo, count);
//        }
//    }



}
