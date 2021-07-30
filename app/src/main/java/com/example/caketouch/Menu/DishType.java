package com.example.caketouch.Menu;

/**
 * 菜的类型
 */
public enum  DishType {
        other(1),
        yao(2),        //窑
        soup(3) ,      //汤
        saute(4),      //炒
        pot(5),        //煲
        fry(6),        //炸,
        drink(7)
        ;
        public int code;
        DishType(int code) {
            this.code = code;
        }

}
