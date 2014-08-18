package com.fantasycraft.forgepermittor.nms.Handlers;

import com.fantasycraft.forgepermittor.nms.NMSResolver;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by thomas on 8/17/2014.
 */
public class BlockHandler {

    @Getter
    public NMSResolver nmsResolver;

    public Method method;

    public BlockHandler(NMSResolver nmsResolver){
        this.nmsResolver = nmsResolver;

        try {
            //New Forge methode
            method =  getNmsResolver().getBlock().getMethod("hasTileEntity", int.class);
        } catch (NoSuchMethodException e) {
            try {
                //Depricated Forge Methode
                method =  getNmsResolver().getBlock().getMethod("hasTileEntity");
            } catch (NoSuchMethodException e1) {

            }
        }

    }

    public boolean IsContainer( Object object, byte meta){
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
