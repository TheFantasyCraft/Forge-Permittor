package com.fantasycraft.forgepermittor.nms;

import com.fantasycraft.forgepermittor.nms.handlers.*;
import lombok.Data;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by thomas on 8/16/2014.
 */
@Data
public class NMSResolver {

    //craftbukkit
    private Class CraftItemStack;
    private Class CraftOfflinePlayer;
    private Class CraftWorld;
    //nms
    private Class ItemStack;
    private Class NBTTagCompound;
    private Class Block;
    private Class Item;
    private Class BlockContainer;
    private Class ItemSword;
    private Class ItemFood;
    private Class ItemBlock;
    private Class TileEntity;


    private ItemList BlockList;
    private ItemList ItemList;

    BlockHandler blockHandler;
    CraftWorldHandler craftWorldHandler;
    CraftItemStackHandler craftItemStackHandler;
    ItemStackHandler itemStackHandler;
    ItemBlockHandler itemBlockHandler;

    final private String CraftbukkitLocation = "org.bukkit.craftbukkit";

    @Getter
    private String craftbukkit;

    public NMSResolver() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        this.craftbukkit = findCraftBukkit();
        this.CraftItemStack = Class.forName(getCraftbukkit() + ".inventory.CraftItemStack");
        this.CraftOfflinePlayer = Class.forName(getCraftbukkit() + ".CraftOfflinePlayer");
        this.CraftWorld = Class.forName(getCraftbukkit() + ".CraftWorld");

        this.NBTTagCompound = getPrivatemethode("getData", getCraftOfflinePlayer()).getReturnType();
        this.ItemStack = FindConstructorSingleParameter(this.getCraftItemStack());
        this.TileEntity = getCraftWorld().getMethod("getTileEntityAt", int.class, int.class, int.class).getReturnType();

        for (Constructor c : this.getItemStack().getConstructors()) {
            if (c.getParameterTypes().length == 1 && !c.getParameterTypes()[0].isPrimitive()) {
                ItemList list = new ItemList(c.getParameterTypes()[0]);
                if (list.get(0) != null || list.getLength() == 4096){
                    Block = c.getParameterTypes()[0];
                    this.BlockList = list;
                }else{
                    Item = c.getParameterTypes()[0];
                    this.ItemList = list;
                }
                if (Block != null && Item != null)
                    break;
            }
        }

        this.BlockContainer = getBlockList().get(54).getClass().getSuperclass();
        this.ItemSword = getItemList().get(267).getClass();
        this.ItemFood = getItemList().get(319).getClass();
        this.ItemBlock = getItemList().get(106).getClass().getSuperclass();

        this.blockHandler = new BlockHandler(this);
        this.craftWorldHandler = new CraftWorldHandler(this);
        this.itemStackHandler = new ItemStackHandler(this);
        this.craftItemStackHandler = new CraftItemStackHandler(this);
        this.itemBlockHandler = new ItemBlockHandler(this);

        System.out.println(getItemList().get(1));
        System.out.println(getBlockList().get(1));
    }

    private String findCraftBukkit(){
        Package Craftbukkit = Package.getPackage(CraftbukkitLocation);
        for (Package e : Craftbukkit.getPackages()){
            if (e.getName().startsWith(CraftbukkitLocation + ".v")) {
                return CraftbukkitLocation + "." + e.getName().split("\\.")[3];
            }
        }
        return CraftbukkitLocation;
    }

    private Class FindConstructorSingleParameter(Class ClasS){
        for (Constructor c : ClasS.getDeclaredConstructors()){
            if (c.getParameterTypes().length == 1 && !c.getParameterTypes()[0].isPrimitive() && !c.getParameterTypes()[0].isAssignableFrom(ItemStack.class)){
                return c.getParameterTypes()[0];
            }
        }
        return null;
    }


    private Method getPrivatemethode(String name, Class ClasS){
        for (Method m : getCraftOfflinePlayer().getDeclaredMethods())
           if (m.getName().equalsIgnoreCase(name))
               return m;
        return null;
    }
}
