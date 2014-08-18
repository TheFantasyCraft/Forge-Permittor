package com.fantasycraft.forgepermittor.nms;

import com.fantasycraft.forgepermittor.info.ItemType;
import com.fantasycraft.forgepermittor.nms.Handlers.ItemBlock;
import com.fantasycraft.forgepermittor.nms.Handlers.NMSItemstack;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 * Created by thomas on 8/17/2014.
 */

public class ItemValidator {

    @Getter
    private NMSResolver nmsResolver;

    public ItemValidator(NMSResolver nmsResolver){
        this.nmsResolver = nmsResolver;
    }

    public ItemType CheckItem(ItemStack stack) {
        int ItemID = stack.getTypeId();
        byte Meta = stack.getData().getData();

        Object item = nmsResolver.getItemList().get(ItemID);
        if (item != null) {
            if (nmsResolver.getItemBlock().isInstance(item))
                return CheckBlock(new ItemBlock(item, nmsResolver).getBlockID(), Meta);
            else if (nmsResolver.getItemFood().isInstance(item))
                return ItemType.Food;
            else if (nmsResolver.getItemSword().isInstance(item))
                return ItemType.Sword;
            else if (nmsResolver.getItem().isInstance(item)) {
                if (new NMSItemstack(stack, nmsResolver).HasTagCompound())
                    return ItemType.AdvItem;
                return ItemType.Item;
            }
        }
        return ItemType.Unknown;
    }

    public ItemType CheckBlock(Block block){
       return CheckBlock(block.getTypeId(), block.getData());

    }

    public ItemType CheckBlock(int ItemID, byte meta){
        if (ItemID < 4096) {
            Object object = nmsResolver.getBlockList().get(ItemID);
            if (nmsResolver.getBlockContainer().isInstance(object))
                return ItemType.Container;
            if (nmsResolver.getBlock().isInstance(object)){
                com.fantasycraft.forgepermittor.nms.Handlers.Block block =
                        new com.fantasycraft.forgepermittor.nms.Handlers.Block(object, nmsResolver);
                if (block.IsContainer(meta))
                    return ItemType.Container;
                else
                    return ItemType.Block;
            }

        }
        return ItemType.Unknown;
    }


}
