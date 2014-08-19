package com.fantasycraft.forgepermittor.nms;

import lombok.Getter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Created by thomas on 8/17/2014.
 */
public class ItemList {

    //Works for Item and Block class

    @Getter
    private Class ItemClass;
    private Object list;

    public ItemList(Class ItemClass){
        this.ItemClass = ItemClass;
        this.list =  findlist();
    }

    private Object findlist(){
        try {
            for (Field field : this.getItemClass().getFields()) {
                if (field.getType().isArray())
                    return field.get(null);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object get(int ID){
        return Array.get(list, ID);
    }

    public int getLength() {
        return Array.getLength(list);
    }
}
