package com.example.caketouch.Menu;

public enum DishUnit {
    fen(0),
    pan(1),
    guan(2),
    ping(3),
    ge(4),
    jin(5);
    public int code;
    DishUnit(int code){
        this.code = code;
    }

    public static DishUnit getDishUnit(String str){
        if (str == null)return fen;
        if (str.equals("份"))return fen;
        else if (str.equals("盘"))return pan;
        else if (str.equals("罐"))return guan;
        else if (str.equals("瓶"))return ping;
        else if (str.equals("个"))return ge;
        else if (str.equals("斤"))return jin;

        return fen;
    }
    public static String getUnitStr(DishUnit dishUnit){
        switch (dishUnit){
            case fen:
                return "份";
            case pan:
                return "盘";
            case guan:
                return "罐";
            case ping:
                return "瓶";
            case ge:
                return "个";
            case jin:
                return "斤";
        }
        return "无";
    }
}
