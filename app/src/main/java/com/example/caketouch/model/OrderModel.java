package com.example.caketouch.model;

import com.example.caketouch.table.Stuff;

import java.util.HashMap;

public class OrderModel {
    private int tableNo;
    private HashMap<Long, Stuff> ordered = new HashMap();
    private Long orderTime;     //点餐时间
    private float total;        //总额
}
