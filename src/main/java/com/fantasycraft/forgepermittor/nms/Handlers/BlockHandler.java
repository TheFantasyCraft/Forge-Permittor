package com.fantasycraft.forgepermittor.nms.Handlers;

import com.fantasycraft.forgepermittor.ForgePermittor;
import com.fantasycraft.forgepermittor.nms.NMSResolver;
import com.fantasycraft.forgepermittor.nms.util.Util;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by thomas on 8/17/2014.
 */
public class BlockHandler {

    @Getter
    public NMSResolver nmsResolver;

    public Method HasTileEntity;

    public BlockHandler(NMSResolver nmsResolver){
        this.nmsResolver = nmsResolver;

        try {
            HasTileEntity =  getNmsResolver().getBlock().getMethod("hasTileEntity", int.class);
        } catch (NoSuchMethodException e) {
            ForgePermittor.log("Outdated Forge ( or updated Bukkit xD ) !! Using Other methode to find Container Block on Items", false);
        }
    }

    public boolean IsContainer(Object object,int ID,  byte meta){
        if (HasTileEntity == null) {
            //Smells like an OutDated Forge or Craftbukkit Server, Its good that i have the brains to figure out another way to get this!!
            System.out.println("Smells like Bukkit");
            return Util.ClassHasmethodeWithReturnType(nmsResolver.getBlockList().get(ID).getClass(), nmsResolver.getTileEntity(), nmsResolver.getBlock());
        }
        System.out.println("Oh lord Forge <3!");
        try {
            return (Boolean) HasTileEntity.invoke(object, (int) meta );
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return true;
    }
}