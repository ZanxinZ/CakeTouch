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
        drink(7)       //饮品
        ;
        public int code;
        DishType(int code) {
            this.code = code;
        }

        public static DishType getDishType(String str){
                if (str.equals("其他"))return other;
                if (str.equals("窑"))return yao;
                if (str.equals("汤"))return soup;
                if (str.equals("炒"))return saute;
                if (str.equals("煲"))return pot;
                if (str.equals("炸"))return fry;
                if (str.equals("饮品"))return drink;
                return other;

        }
        public static String getType(DishType dishType){
                switch (dishType){
                        case other:
                                return"其它";
                        case yao:
                                return"窑";
                        case soup:
                                return"汤";
                        case saute:
                                return"炒";
                        case pot:
                                return"煲";
                        case fry:
                                return"炸";
                        case drink:
                                return"饮料";
                }
                return "空";
        }

}
