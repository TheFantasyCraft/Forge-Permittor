package com.fantasycraft.forgepermittor.nms.Handlers;

import com.fantasycraft.forgepermittor.nms.NMSResolver;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    public int getBlockID(Object object){
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
