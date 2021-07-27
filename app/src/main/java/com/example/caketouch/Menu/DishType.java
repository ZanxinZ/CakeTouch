package com.example.caketouch.Menu;

/**
 * 菜的类型
 */
public enum  DishType {
        yao(1),        //窑
        soup(2) ,      //汤
        pot(3),        //煲
        saute(4),      //炒
        fry(5),        //炸
        vegetable(6),  //青菜
        other(7),
        drink(8)
        ;
        public int code;
        DishType(int code) {
            this.code = code;
        }

}
