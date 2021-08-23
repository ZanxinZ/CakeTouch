package com.example.caketouch.food_for_serve;

import com.example.caketouch.table.Stuff;

import java.util.ArrayList;

public class TableOrdered {
    int people = 0;
    int notServedCount = 0;
    int servedCount = 0;
    ArrayList<Stuff> stuffs = new ArrayList<>();

    public TableOrdered(int people){
        this.people = people;
    }

    public void attachStuffToTable(Stuff stuff){
        stuffs.add(stuff);
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public ArrayList<Stuff> getStuffs() {
        return stuffs;
    }

    public void setStuffs(ArrayList<Stuff> stuffs) {
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
