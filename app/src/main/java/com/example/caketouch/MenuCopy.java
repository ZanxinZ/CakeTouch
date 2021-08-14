package com.example.caketouch;

import android.util.Log;

import com.example.caketouch.table.Stuff;

import java.util.HashMap;

/**
 * 菜单副本
 */
public class MenuCopy {
    //统计已点物品以及数量。
    private HashMap<Long, Stuff> ordered = new HashMap<>();

    public void addStuff(Stuff stuff){
        if (ordered.containsKey(stuff.getDishNo())){
            Stuff target = ordered.get(stuff.getDishNo());
            if (target == null){
                Log.d("错误：", "MenuCopy物品数量增加错误。");
                return;
            }
           //target.setCount(stuff.getCount() + target.getCount());

        }else{
            ordered.put(stuff.getDishNo(),stuff);
        }
    }

    /**
     * 统计已点物品以及数量。
     * @return
     */
    public HashMap<Long, Stuff> getOrdered(){
        return ordered;
    }
}
