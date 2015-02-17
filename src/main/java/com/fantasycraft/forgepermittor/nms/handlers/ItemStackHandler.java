package com.fantasycraft.forgepermittor.nms.handlers;

import com.fantasycraft.forgepermittor.nms.NMSResolver;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * Created by thomas on 8/19/2014.
 */
public class ItemStackHandler {
    @Getter
    private NMSResolver nmsResolver;

    private Method HasTagCompound;

    public ItemStackHandler(NMSResolver nmsResolver){
        this.nmsResolver = nmsResolver;

        for (Method m : getNmsResolver().getItemStack().getDeclaredMethods())
            if (m.getReturnType().isAssignableFrom(nmsResolver.getNBTTagCompound()) && m.getParameterTypes().length == 0){
                HasTagCompound = m;
                break;
            }
    }

    public boolean HasTagCompound(Object object){
        try {
            return HasTagCompound.invoke(object) != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

	public NMSResolver getNmsResolver() {
		return nmsResolver;
	}

	public Method getHasTagCompound() {
		return HasTagCompound;
	}


}
