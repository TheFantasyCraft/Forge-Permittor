package com.fantasycraft.forgepermittor.nms.handlers;

import com.fantasycraft.forgepermittor.nms.NMSResolver;
import com.fantasycraft.forgepermittor.nms.util.Util;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by thomas on 8/17/2014.
 */
public class ItemBlockHandler {

    @Getter
    private NMSResolver nmsResolver;

    private Method method;

    public ItemBlockHandler(NMSResolver nmsResolver) {
        this.nmsResolver = nmsResolver;

        for (Method m : nmsResolver.getItemBlock().getDeclaredMethods() ){
            if (m.getReturnType().isAssignableFrom(int.class) && m.getParameterTypes().length == 0 ) {
                method = m;
                break;
            }
        }
    }

    public Object getBlock(Object Item) throws InvocationTargetException, IllegalAccessException {
        int ID = getBlockID(Item);
        if (ID != -1)
            return getNmsResolver().getBlockList().get(ID);
        else {
            Class block = getNmsResolver().getBlock();
            Method method = Util.getMethode(block, block, Modifier.STATIC + Modifier.PUBLIC, getNmsResolver().getItem());
            if (method != null)
                return method.invoke(null, Item);
            else
                return Item;
        }

    }

    public int getBlockID(Object object){
        try {
            return (Integer) method.invoke(object);
        }
        catch (Exception e) {
            return -1;
        }
    }

	public NMSResolver getNmsResolver() {
		return nmsResolver;
	}

	public Method getMethod() {
		return method;
	}
}
