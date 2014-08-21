package com.fantasycraft.forgepermittor.info;

import com.fantasycraft.forgepermittor.ForgePermittor;
import com.fantasycraft.forgepermittor.info.types.BlockType;
import com.fantasycraft.forgepermittor.info.types.ItemType;
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
    InformationManager informationManager;

    public ItemValidator(NMSResolver nmsResolver){
        this.nmsResolver = nmsResolver;
        this.informationManager = new InformationManager(getNmsResolver());
    }

    public ItemType CheckItem(ItemStack stack) throws InvocationTargetException, IllegalAccessException {
        int ItemID = stack.getTypeId();
        byte meta = stack.getData().getData();
        Object item = nmsResolver.getItemList().get(ItemID);
        ForgePermittor.log(item.toString(), true);
        if (item != null) {
          /*  if (this.getInformationManager().HasConnectableInferface(item))
                return ItemType.Connectable;*/
            if (this.getInformationManager().HasItemBlockContainerInterface(item))
                return ItemType.Container;
            if (this.getInformationManager().HasFoodInterface(item))
                return ItemType.Food;
            if (this.getInformationManager().HasSwordInterface(item))
                return ItemType.Sword;
            if (this.getInformationManager().HasItemBlockInterface(item))
                return CheckItemBlock(item, ItemID, meta);
            if (this.getInformationManager().HasItemInterface(item)) {
                if (nmsResolver.getItemStackHandler().HasTagCompound(nmsResolver.getCraftItemStackHandler().asNMSCopy(stack)))
                    return ItemType.AdvItem;
                return ItemType.Item;
            }
        }
        return ItemType.Unknown;
    }


    public BlockType CheckBlock(Block block) throws InvocationTargetException, IllegalAccessException {
        if (block.getTypeId() < 4096) {
            Object object = nmsResolver.getBlockList().get(block.getTypeId());
            ForgePermittor.log(object.toString(), true);
            if ( this.getInformationManager().HasContainerInterface(object) ||nmsResolver.getCraftWorldHandler().HasTileEntity(block))
                return BlockType.Container; //CheckConnectable(block);
            if (this.getInformationManager().HasBlockInterface(object))
                    return BlockType.Block;


        }
        return BlockType.Unknown;
    }

  /*  private BlockInfo CheckConnectable(Block block){
        if (this.getInformationManager().IsConnectable(block))
            return BlockInfo.Connectable;
        else
            return BlockInfo.Container;

    }*/

    private ItemType CheckItemBlock(Object item, int ItemID, byte meta) throws InvocationTargetException, IllegalAccessException {
        if (ItemID < nmsResolver.getBlockList().getLength()) {
            Object object = nmsResolver.getItemBlockHandler().getBlock(item);
            if (this.getInformationManager().HasContainerInterface(object) || nmsResolver.getBlockHandler().IsContainer(object, meta) )
                return ItemType.Container;
            else if (this.getInformationManager().HasBlockInterface(object)){
                return ItemType.Block;
            }
        }
        return ItemType.Block;
    }
}
