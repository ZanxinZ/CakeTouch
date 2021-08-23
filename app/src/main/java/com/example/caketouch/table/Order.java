package com.example.caketouch.table;


import com.example.caketouch.food_for_serve.AllOrdered;
import com.example.caketouch.food_for_serve.FoodOrdered;
import com.example.caketouch.menu.Dish;
import com.example.caketouch.menu.DishType;
import com.example.caketouch.menu.DishUnit;

import java.util.Date;
import java.util.HashMap;

/**
 * 记录点的每一个物品，每个物品有一个ID(time)，相同的菜色有相同的 DishNo
 */
public class Order {
    //记录点的每个物品
    public HashMap<Long, Stuff> ordered = new HashMap<>();   // stuffId, stuff
    //public HashMap<Long, Stuff> served = new HashMap<>();    // stuffId, stuff

    private static Long orderTime;//点餐时间
    private static float total;     //总价
    public Order() {
        Order.orderTime = new Date().getTime();
    }

    public Stuff orderStuff(Dish dish, boolean isNormal, int count){
        if (count < 1 || count > 101)return null;
        Stuff stuff = null;


        //exist BUG
        if (dish.getDishType() == DishType.drink){
            for (int i = 0; i < count; i++) {
                if (isNormal){
                    stuff = new Food(dish.getName(), DishUnit.getUnitStr(dish.getUnit()),
                            dish.getPrice(), new Date().getTime(), dish.getDishNo());
                }else {
                    stuff = new Food(dish.getName(), DishUnit.getUnitStr(dish.getUnit()),
                            dish.getSmallPrice(), new Date().getTime(), dish.getDishNo());
                }
                ordered.put(stuff.getID(), stuff);
            }
        }
        for (int i = 0; i < count; i++) {
            if (isNormal){
                stuff = new Food(dish.getName(), DishUnit.getUnitStr(dish.getUnit()),
                        dish.getPrice(), new Date().getTime(), dish.getDishNo());
            }else {
                stuff = new Food(dish.getName(), DishUnit.getUnitStr(dish.getUnit()),
                        dish.getSmallPrice(), new Date().getTime(), dish.getDishNo());
            }
            ordered.put(stuff.getID(), stuff);
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
