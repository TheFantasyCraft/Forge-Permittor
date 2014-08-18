package com.fantasycraft.forgepermittor.info;

import com.fantasycraft.forgepermittor.nms.ItemValidator;
import com.fantasycraft.forgepermittor.nms.NMSResolver;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomas on 8/18/2014.
 */
public class CachedItemValidator extends ItemValidator {

    @Getter
    Map<ItemInfo, ItemType> FoundedBlocks;

    public CachedItemValidator(NMSResolver nmsResolver) {
        super(nmsResolver);
        this.FoundedBlocks = new HashMap<ItemInfo, ItemType>();
    }

    @Override
    public ItemType CheckBlock(Block block) {
        return super.CheckBlock(block);
    }

    @Override
    public ItemType CheckBlock(int ItemID, byte meta) {
        return super.CheckBlock(ItemID, meta);
    }

    @Override
    public ItemType CheckItem(ItemStack stack) {
        return super.CheckItem(stack);
    }
}
