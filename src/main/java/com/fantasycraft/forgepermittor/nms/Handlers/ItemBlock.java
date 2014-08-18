package com.fantasycraft.forgepermittor.nms.Handlers;

import com.fantasycraft.forgepermittor.nms.NMSResolver;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by thomas on 8/17/2014.
 */
public class ItemBlock {

    @Getter
    private Object object;
    @Getter
    private NMSResolver nmsResolver;

    private Method method;

    public ItemBlock(Object object, NMSResolver nmsResolver) {
        this.object = object;
        this.nmsResolver = nmsResolver;

        for (Method m : nmsResolver.getItemBlock().getDeclaredMethods() ){
            if (m.getReturnType().isAssignableFrom(int.class) && m.getParameterTypes().length == 0 ) {
                method = m;
                break;
            }
        }

    }

    public int getBlockID(){
        try {
            return (Integer) method.invoke(object);
        }
         catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
