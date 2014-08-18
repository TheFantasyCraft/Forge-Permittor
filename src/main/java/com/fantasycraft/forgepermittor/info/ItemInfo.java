package com.fantasycraft.forgepermittor.info;

import lombok.Data;
import lombok.ToString;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 * Created by thomas on 8/18/2014.
 */

@ToString
@Data
public class ItemInfo {
    private int ID;
    private byte Meta;

    public ItemInfo(int ID, byte Meta){
        this.ID = ID;
        this.Meta = Meta;
    }

    public ItemInfo(Block block){
        this(block.getTypeId(), block.getData());
    }

    public ItemInfo(ItemStack itemStack){
        this(itemStack.getTypeId(), itemStack.getData().getData());
    }
}
