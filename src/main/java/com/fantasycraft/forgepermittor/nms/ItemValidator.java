package com.fantasycraft.forgepermittor.nms;

import com.fantasycraft.forgepermittor.ForgePermittor;
import com.fantasycraft.forgepermittor.info.BlockInfo;
import com.fantasycraft.forgepermittor.info.ItemType;
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

    public ItemValidator(NMSResolver nmsResolver){
        this.nmsResolver = nmsResolver;
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
            if (nmsResolver.getBlockContainer().isInstance(object))
                return BlockInfo.Container;
            if (nmsResolver.getBlock().isInstance(object)){
                if (nmsResolver.getCraftWorldHandler().HasTileEntity(block) )
                    return BlockInfo.Container;
                else
                    return BlockInfo.Block;
            }

        }
        return BlockInfo.Unknown;
    }

    private ItemType CheckItem(Object item , int ItemID, byte meta) throws InvocationTargetException, IllegalAccessException {
        if (ItemID < nmsResolver.getBlockList().getLength()) {
            Object object = nmsResolver.getItemBlockHandler().getBlock(item);
            if (nmsResolver.getBlockContainer().isInstance(object))
                return ItemType.Container;
            else if (nmsResolver.getBlock().isInstance(object)){
                if (nmsResolver.getBlockHandler().IsContainer(object, meta))
                    return ItemType.Container;
                else
                    return ItemType.Block;
            }
        }
        return ItemType.Unknown;
    }


}
