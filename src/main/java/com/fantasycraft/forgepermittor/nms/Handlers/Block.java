package com.fantasycraft.forgepermittor.nms.Handlers;

import com.fantasycraft.forgepermittor.nms.NMSResolver;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by thomas on 8/17/2014.
 */
public class Block {

    @Getter
    public Object object;
    @Getter
    public NMSResolver nmsResolver;

    public Method method;

    public Block (Object object, NMSResolver nmsResolver){
        this.object = object;
        this.nmsResolver = nmsResolver;

        try {
            method =  getNmsResolver().getBlock().getMethod("hasTileEntity", int.class);
            if (method == null)
                method =  getNmsResolver().getBlock().getMethod("hasTileEntity");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public boolean IsContainer(byte meta){
        try {
            return (Boolean) method.invoke(object, (int) meta );
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return true;
    }

}
