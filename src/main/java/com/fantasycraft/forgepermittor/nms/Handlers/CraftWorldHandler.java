package com.fantasycraft.forgepermittor.nms.Handlers;

import com.fantasycraft.forgepermittor.nms.NMSResolver;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by thomas on 8/19/2014.
 */
public class CraftWorldHandler {
    @Getter
    public NMSResolver nmsResolver;

    public Method method;

    public CraftWorldHandler(NMSResolver nmsResolver) throws NoSuchMethodException {
        this.nmsResolver = nmsResolver;
        method = nmsResolver.getCraftWorld().getMethod("getTileEntityAt", int.class, int.class, int.class);
    }

    public boolean HasTileEntity(World world, int x, int y, int z){
        Object craftworld = nmsResolver.getCraftWorld().cast(world);
        try {
            return null != method.invoke(craftworld, x, y, z);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean HasTileEntity(Block block){
        return this.HasTileEntity(block.getWorld(), block.getX(), block.getY(), block.getZ());
    }


}
