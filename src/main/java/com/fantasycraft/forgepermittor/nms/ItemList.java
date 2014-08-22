package com.fantasycraft.forgepermittor.nms;

import com.fantasycraft.forgepermittor.ForgePermittor;
import com.fantasycraft.forgepermittor.nms.util.Util;
import lombok.Getter;

import java.lang.reflect.*;

/**
 * Created by thomas on 8/17/2014.
 */
public class ItemList {

    //Works for Item and Block class

    @Getter
    private Class ItemClass;

    //1.6.4 and lower
    private Object list;

    //1.7.2 and higher
    private Method GetBlock;
    private Method GetBlockString;

    @Getter
    private int Length;

    public ItemList(Class ItemClass) throws IllegalAccessException {
        this.ItemClass = ItemClass;
        findlist();
    }

    private void findlist() throws IllegalAccessException {
        for (Field field : this.getItemClass().getFields()) {
            if (field.getType().isArray() && field.getType().getComponentType().isAssignableFrom(getItemClass())) {
                this.list = field.get(null);
                this.Length = Array.getLength(list);
                return;
            }
        }

        //Methods For 1.7.X
        this.GetBlock = Util.getMethode(getItemClass(), getItemClass(), Modifier.STATIC + Modifier.PUBLIC , int.class);
        this.GetBlockString = Util.getMethode(getItemClass(), getItemClass(), Modifier.STATIC + Modifier.PUBLIC  , String.class);

        Length = Integer.MAX_VALUE;

    }

    public Object get(int ID) throws InvocationTargetException, IllegalAccessException {
        ForgePermittor.log("Requested ID: " + ID, true);
        if (list != null)
            return Array.get(list, ID);
        else
            return GetBlock.invoke(null, ID);

    }

    @Deprecated //simply not working
    public Object get(String ID) throws InvocationTargetException, IllegalAccessException {
        if (list != null)
            return Array.get(list, Integer.parseInt(ID));
        else
            return GetBlockString.invoke(null, ID);

    }
}
