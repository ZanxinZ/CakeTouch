package com.example.caketouch.table;



import com.example.caketouch.food_for_serve.DrinkOrdered;
import com.example.caketouch.menu.Dish;
import com.example.caketouch.menu.DishType;
import com.example.caketouch.menu.DishUnit;
import com.example.caketouch.util.DateGet;

import java.io.Serializable;
import java.util.Date;
import java.util.TreeMap;

/**
 * 记录点的每一个物品，每个物品有一个ID(time)，相同的菜色有相同的 DishNo
 */
public class Order implements Serializable {
    //记录点的每个物品
    public TreeMap<Long, Stuff> ordered = new TreeMap<>();   // stuffId, stuff //foods
    public TreeMap<Long, DrinkOrdered> drinkOrderedMap = new TreeMap<>(); // DishNo, drinkOrdered
    private static Long orderTime;//点餐时间
    private float total = 0;      //总价
    public Order() {
        Order.orderTime = new Date().getTime();
    }

    public Stuff orderStuff(Dish dish, boolean isNormal, int count){
        if (count < 1 || count > 101)return null;
        Stuff stuff = null;

        if (dish.getDishType() == DishType.drink){

            DrinkOrdered drinkOrdered;
            if (drinkOrderedMap.containsKey(dish.getDishNo())){
                drinkOrdered = drinkOrderedMap.get(dish.getDishNo());
            }else{
                drinkOrdered = new DrinkOrdered(dish.getDishNo(), 0, dish.getName(),DishUnit.getUnitStr(dish.getUnit()),
                        dish.getPrice());
            }
            assert drinkOrdered != null;
            drinkOrdered.setCount(drinkOrdered.getCount() + count);
            drinkOrderedMap.put(drinkOrdered.getDishNo(), drinkOrdered);
            total += drinkOrdered.getMoney() * count;
            //drink type will return a null stuff.
        }
        else{
            for (int i = 0; i < count; i++) {
                if (isNormal){
                    stuff = new Food(dish.getName(), DishUnit.getUnitStr(dish.getUnit()),
                            dish.getPrice(), DateGet.Time(), dish.getDishNo(), dish.getDishType(), StuffSize.normal);
                }else {
                    stuff = new Food(dish.getName(), DishUnit.getUnitStr(dish.getUnit()),
                            dish.getSmallPrice(), DateGet.Time(), dish.getDishNo(),dish.getDishType(),StuffSize.small);
                }
                ordered.put(stuff.getID(), stuff);
                total += stuff.getPrice();
                // Because use the mills as the ID, it can't be duplicate.
                // if continuously put to hashmap, it's so fast that the mills time number will be the same, so wait for 1 millisecond
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


    public float getTotal() {
        return total;
    }
}
