package com.fantasycraft.forgepermittor.nms;

import com.fantasycraft.forgepermittor.nms.handlers.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by thomas on 8/16/2014.
 */
@Data
@Setter(AccessLevel.PRIVATE)
public class NMSResolver {

    //craftbukkit
    private Class CraftItemStack;
    private Class CraftOfflinePlayer;
    private Class CraftWorld;
    private Class CraftInventory;
    //nms
    private Class ItemStack;
    private Class NBTTagCompound;
    private Class Block;
    private Class Item;
    private Class BlockContainer;
    private Class ItemSword;
    private Class ItemFood;
    private Class ItemBlock;
    private Class ItemDoor;
    private Class TileEntity;
    private Class IInventory;
    private Class ItemBow;
    private Class SignBlock;


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
        this.CraftInventory = Class.forName(getCraftbukkit() + ".inventory.CraftInventory");

        this.NBTTagCompound = getPrivatemethode("getData", getCraftOfflinePlayer()).getReturnType();
        this.ItemStack = FindConstructorSingleParameter(this.getCraftItemStack());
        this.IInventory = CraftInventory.getMethod("getInventory").getReturnType();
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

        this.BlockContainer = getBlockList().get(Material.CHEST.getId()).getClass().getSuperclass();
        this.ItemSword = getItemList().get(Material.IRON_SWORD.getId()).getClass();
        this.ItemFood = getItemList().get(Material.PORK.getId()).getClass();
        this.ItemBlock = getItemList().get(Material.VINE.getId()).getClass().getSuperclass();
        this.ItemDoor = getItemList().get(Material.IRON_DOOR.getId()).getClass();
        this.setItemBow(getItemList().get(Material.BOW.getId()).getClass());
        this.setSignBlock(getBlockList().get(Material.WALL_SIGN.getId()).getClass());

        this.blockHandler = new BlockHandler(this);
        this.craftWorldHandler = new CraftWorldHandler(this);
        this.itemStackHandler = new ItemStackHandler(this);
        this.craftItemStackHandler = new CraftItemStackHandler(this);
        this.itemBlockHandler = new ItemBlockHandler(this);

        System.out.println(getItemList().get(1));
        System.out.println(getBlockList().get(1));
    }

    private String findCraftBukkit(){
        for (StackTraceElement e : Thread.currentThread().getStackTrace()){
            if (e.getClassName().startsWith(CraftbukkitLocation + ".v")) {
                return CraftbukkitLocation + "." + e.getClassName().split("\\.")[3];
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
