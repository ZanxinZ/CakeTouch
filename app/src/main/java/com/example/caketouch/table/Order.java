package com.example.caketouch.table;


import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.caketouch.Menu.Dish;
import com.example.caketouch.Menu.DishUnit;

import java.util.Date;
import java.util.HashMap;

/**
 * 记录点的每一个物品，每个物品有一个ID(time)，相同的菜色有相同的 DishNo
 */
public class Order {
    //记录点的每个物品
    private HashMap<Long, Stuff> ordered = new HashMap();
    private Long orderTime;//点餐时间

    public Order() {
        this.orderTime = new Date().getTime();
    }

    /**
     * 点菜
     * @param dish
     * @param count
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void orderFood(int tableNo, Dish dish, int count){
        Food food = new Food(dish.getName(), DishUnit.getUnitStr(dish.getUnit()), dish.getPrice(), dish.getSmallPrice(), count, new Date().getTime(), dish.getDishNo(), tableNo);
        ordered.put(food.getTime(),food);
    }

    /**
     * 拿饮料
     * @param dish
     * @param count
     */
    public void takeDrink(int tableNo, Dish dish, int count){
        Drink drink = new Drink(dish.getName(),DishUnit.getUnitStr(dish.getUnit()), dish.getPrice(),-1, count, new Date().getTime(),dish.getDishNo(), tableNo);
        ordered.put(drink.getTime(),drink);
    }

    /**
     * 上菜或饮料
     * @param stuff
     */
    public void serveStuff(Stuff stuff){
        stuff.serve();
    }

    public Long getOrderTime() {
        return orderTime;
    }
}
