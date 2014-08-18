package com.fantasycraft.forgepermittor.nms.Handlers;

import com.fantasycraft.forgepermittor.nms.NMSResolver;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by thomas on 8/17/2014.
 */
public class NMSItemstack {
    @Getter
    public Object object;
    @Getter
    public NMSResolver nmsResolver;

    public NMSItemstack(ItemStack stack, NMSResolver nmsResolver) {
        try {
            this.object = nmsResolver.getCraftItemStack().getMethod("asNMSCopy", ItemStack.class).invoke( null, stack);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.nmsResolver = nmsResolver;
    }

    public boolean HasTagCompound(){
        Method method = null;
        for (Method m : nmsResolver.getItemStack().getDeclaredMethods())
            if (m.getReturnType().isAssignableFrom(nmsResolver.getNBTTagCompound()) && m.getParameterTypes().length == 0){
                method = m;
            }
        try {
           return method.invoke(object) != null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;

    }
}
