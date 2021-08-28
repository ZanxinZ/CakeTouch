package com.example.caketouch.food_for_serve;

import com.example.caketouch.table.Stuff;

import java.util.ArrayList;
import java.util.HashMap;

public class TableOrdered {
    int people = 0;
    int notServedCount = 0;
    int servedCount = 0;
    //ArrayList<Stuff> stuffs = new ArrayList<>();
    HashMap<Long, Stuff> stuffs = new HashMap<>();//stuffNo, Stuff

    public TableOrdered(int people){
        this.people = people;
    }

    public void attachStuffToTable(Stuff stuff){
        stuffs.put(stuff.getID(),stuff);
    }

    public void removeStuffFromTable(Long stuffNo){
        stuffs.remove(stuffNo);
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public HashMap<Long, Stuff> getStuffs() {
        return stuffs;
    }

    public void setStuffs(HashMap<Long, Stuff> stuffs) {
        this.stuffs = stuffs;
    }

    public int getNotServedCount() {
        return notServedCount;
    }

    public void setNotServedCount(int notServedCount) {
        this.notServedCount = notServedCount;
    }

    public int getServedCount() {
        return servedCount;
    }

    public void setServedCount(int servedCount) {
        this.servedCount = servedCount;
    }
}
