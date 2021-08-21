package com.example.caketouch.table;


import com.example.caketouch.food_for_serve.AllOrdered;
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
    public static HashMap<Long, Stuff> ordered = new HashMap<>();   // stuffId, stuff
    public static HashMap<Long, Stuff> served = new HashMap<>();    // stuffId, stuff

    private static Long orderTime;//点餐时间
    private static float total;     //总价
    public Order() {
        Order.orderTime = new Date().getTime();
    }

    public Stuff orderStuff(Dish dish, boolean isNormal, int count, int tableNo){
        if (count < 1 || count > 101)return null;
        Stuff stuff = null;
        if (dish.getDishType() == DishType.drink){
            for (int i = 0; i < count; i++) {
                Drink drink;
                if (isNormal){
                    drink = new Drink(dish.getName(), DishUnit.getUnitStr(dish.getUnit()),
                            dish.getPrice(), new Date().getTime(), dish.getDishNo(), tableNo);
                }else {
                    drink = new Drink(dish.getName(), DishUnit.getUnitStr(dish.getUnit()),
                            dish.getSmallPrice(), new Date().getTime(), dish.getDishNo(), tableNo);
                }
                stuff = drink;
                ordered.put(drink.getID(), drink);
            }


        }else{
            for (int i = 0; i < count; i++) {
                Food food;
                if (isNormal){
                    food = new Food(dish.getName(), DishUnit.getUnitStr(dish.getUnit()),
                            dish.getPrice(), new Date().getTime(), dish.getDishNo(), tableNo);
                }else {
                    food = new Food(dish.getName(), DishUnit.getUnitStr(dish.getUnit()),
                            dish.getSmallPrice(), new Date().getTime(), dish.getDishNo(), tableNo);
                }
                stuff = food;
                ordered.put(food.getID(), food);
            }
        }
        addToDishRecord(stuff.getDishNo(), count);
        return stuff;
    }


    /**
     * 上菜或饮料
     * @param stuff
     */
    public void serveStuff(Stuff stuff){

    }

    public Long getOrderTime() {
        return orderTime;
    }

    public void addToDishRecord(Long dishNo, int count){
        if (AllOrdered.dishRecord.containsKey(dishNo)){
            AllOrdered.dishRecord.put(dishNo, AllOrdered.dishRecord.get(dishNo) + count);
        }else{
            AllOrdered.dishRecord.put(dishNo, count);
        }
    }

}
