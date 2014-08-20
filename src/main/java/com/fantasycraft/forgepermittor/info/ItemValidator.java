package com.fantasycraft.forgepermittor.info;

import com.fantasycraft.forgepermittor.ForgePermittor;
import com.fantasycraft.forgepermittor.forge.ModInformationManager;
import com.fantasycraft.forgepermittor.nms.NMSResolver;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by thomas on 8/17/2014.
 */

public class ItemValidator {

    @Getter
    private NMSResolver nmsResolver;
    @Getter
    ModInformationManager modInformationManager;

    public ItemValidator(NMSResolver nmsResolver){
        this.nmsResolver = nmsResolver;
        this.modInformationManager = new ModInformationManager(getNmsResolver());
    }

    public ItemType CheckItem(ItemStack stack) throws InvocationTargetException, IllegalAccessException {
        int ItemID = stack.getTypeId();
        byte meta = stack.getData().getData();
        Object item = nmsResolver.getItemList().get(ItemID);
        ForgePermittor.log(item.toString(), true);
        if (item != null) {
            if (nmsResolver.getItemFood().isInstance(item))
                return ItemType.Food;
            else if (nmsResolver.getItemSword().isInstance(item))
                return ItemType.Sword;
            else if (nmsResolver.getItemBlock().isInstance(item))
                return CheckItem( item, ItemID, meta);
            else if (nmsResolver.getItem().isInstance(item)) {
                if (nmsResolver.getItemStackHandler().HasTagCompound(nmsResolver.getCraftItemStackHandler().asNMSCopy(stack)))
                    return ItemType.AdvItem;
                return ItemType.Item;
            }
        }
        return ItemType.Unknown;
    }


    public BlockInfo CheckBlock(Block block) throws InvocationTargetException, IllegalAccessException {
        if (block.getTypeId() < 4096) {
            Object object = nmsResolver.getBlockList().get(block.getTypeId());

            ForgePermittor.log(object.toString(), true);
            if (nmsResolver.getBlockContainer().isInstance(object) || nmsResolver.getCraftWorldHandler().HasTileEntity(block)
                    || getModInformationManager().HasContainerInterface(object.getClass()))
                return CheckConnectable(block);
            if (nmsResolver.getBlock().isInstance(object))
                    return BlockInfo.Block;


        }
        return BlockInfo.Unknown;
    }

    private BlockInfo CheckConnectable(Block block){
        if (!getModInformationManager().IsConnectable(block))
            return BlockInfo.Container;
        else
            return BlockInfo.Connectable;
    }

    private ItemType CheckItem(Object item , int ItemID, byte meta) throws InvocationTargetException, IllegalAccessException {
        if (ItemID < nmsResolver.getBlockList().getLength()) {
            Object object = nmsResolver.getItemBlockHandler().getBlock(item);
            if (nmsResolver.getBlockContainer().isInstance(object) || getModInformationManager().HasItemBlockContainerInterface(item.getClass())
                    || nmsResolver.getBlockHandler().IsContainer(object, meta) )
                return ItemType.Container;
            else if (nmsResolver.getBlock().isInstance(object)){
                return ItemType.Block;
            }
        }
        return ItemType.Unknown;
    }


}
