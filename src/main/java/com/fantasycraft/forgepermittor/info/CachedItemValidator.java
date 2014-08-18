package com.fantasycraft.forgepermittor.info;

import com.fantasycraft.forgepermittor.ForgePermittor;
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
    private Map<ItemInfo, ItemType> FoundedItems;
    @Getter
    private Map<ItemInfo, ItemType> FoundedBlocks;
    public CachedItemValidator(NMSResolver nmsResolver) {
        super(nmsResolver);
        this.FoundedItems = new HashMap<ItemInfo, ItemType>();
        this.FoundedBlocks = new HashMap<ItemInfo, ItemType>();
    }

    @Override
    public ItemType CheckBlock(Block block) {
        ItemInfo itemInfo = new ItemInfo(block);
        ItemType itemType = FoundedBlocks.get(itemInfo);
        if (itemType == null){
            itemType = this.CheckBlock(block);
            FoundedBlocks.put(itemInfo,itemType );
        }
        else
            ForgePermittor.log("Cached!", true);
        return itemType;
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
