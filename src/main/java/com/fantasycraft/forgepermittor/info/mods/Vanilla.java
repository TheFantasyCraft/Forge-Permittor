package com.fantasycraft.forgepermittor.info.mods;

import com.fantasycraft.forgepermittor.info.IMod;
import com.fantasycraft.forgepermittor.nms.NMSResolver;

/**
 * Created by thomas on 8/20/2014.
 */
public class Vanilla extends IMod {

    public Vanilla(NMSResolver nmsResolver){
        //Blocks
        addBlockInterface(nmsResolver.getBlock());
        addContainerInterface(nmsResolver.getBlockContainer());

        //If we just block containers from being placed to containers hehe
        //addConnectablesInterface(nmsResolver.getIInventory());

        //Items
        addItemInterFace(nmsResolver.getItem());
        addItemBlockInterface(nmsResolver.getItemBlock());

        addWeaponInterface(nmsResolver.getItemSword());
        addWeaponInterface(nmsResolver.getItemBow());

        addFoodInterface(nmsResolver.getItemFood());
    }

    @Override
    public String getName() {
        return "Vanilla";
    }
}
