package com.example.caketouch.menu;

/**
 * 菜的类型
 */
public enum  DishType {
        other(0),
        yao(1),        //窑
        soup(2) ,      //汤
        saute(3),      //炒
        pot(4),        //煲
        fry(5),        //炸,
        drink(6)       //饮品
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
                                return"饮品";
                }
                return "空";
        }

}
