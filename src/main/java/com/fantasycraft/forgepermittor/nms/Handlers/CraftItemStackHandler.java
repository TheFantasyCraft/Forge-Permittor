package com.fantasycraft.forgepermittor.nms.Handlers;

import com.fantasycraft.forgepermittor.nms.NMSResolver;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

/**
 * Created by thomas on 8/17/2014.
 */
public class CraftItemStackHandler {
    @Getter
    private NMSResolver nmsResolver;

    private Method asNMSCopymethode;


    public CraftItemStackHandler(NMSResolver nmsResolver) {
        this.nmsResolver = nmsResolver;

        for (Method m : nmsResolver.getCraftItemStack().getDeclaredMethods())
            if (m.getReturnType().isAssignableFrom(getNmsResolver().getItemStack()) && m.getParameterTypes().length == 1 && m.getParameterTypes()[0].isAssignableFrom(ItemStack.class)) {
                asNMSCopymethode = m;
                break;
            }

    }

    public Object asNMSCopy(ItemStack itemstack){
        try {
           return asNMSCopymethode.invoke( null, itemstack);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
