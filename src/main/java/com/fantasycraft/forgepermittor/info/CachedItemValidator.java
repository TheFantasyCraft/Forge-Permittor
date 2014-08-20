package com.fantasycraft.forgepermittor.info;

import com.fantasycraft.forgepermittor.nms.ItemValidator;
import com.fantasycraft.forgepermittor.nms.NMSResolver;
import com.sk89q.worldedit.blocks.BlockType;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomas on 8/18/2014.
 */
public class CachedItemValidator extends ItemValidator {

    @Getter
    private Map<ItemInfo, ItemType> FoundedItems;
    @Getter
    private Map<ItemInfo, BlockType> FoundedBlocks;
    public CachedItemValidator(NMSResolver nmsResolver) {
        super(nmsResolver);
        this.FoundedItems = new HashMap<ItemInfo, ItemType>();
        this.FoundedBlocks = new HashMap<ItemInfo, BlockType>();
    }

  /*  @Override
    public BlockType CheckItem(Block block) {
        ItemInfo itemInfo = new ItemInfo(block);
        BlockType blocktype = FoundedBlocks.get(itemInfo);
        if (blocktype == null){
            blocktype = this.CheckItem(block);
            FoundedBlocks.put(itemInfo,blocktype );
        }
        else
            ForgePermittor.log("Cached!", true);
        return blocktype;
    }

    @Override
    public ItemType CheckItem(ItemStack stack) {
        return super.CheckItem(stack);
    }*/
}
