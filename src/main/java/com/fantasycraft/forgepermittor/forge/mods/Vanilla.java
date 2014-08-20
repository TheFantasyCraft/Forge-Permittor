package com.fantasycraft.forgepermittor.forge.mods;

import com.fantasycraft.forgepermittor.forge.IMod;
import com.fantasycraft.forgepermittor.nms.NMSResolver;

/**
 * Created by thomas on 8/20/2014.
 */
public class Vanilla extends IMod {

    public Vanilla(NMSResolver nmsResolver){
        //Blocks
        addBlockInterface(nmsResolver.getBlock());
        addContainerInterface(nmsResolver.getBlockContainer());

        //Items
        addItemInterFace(nmsResolver.getItem());
        addItemBlockInterface(nmsResolver.getItemBlock());
        addSwordInterface(nmsResolver.getItemSword());
        addFoodInterface(nmsResolver.getItemFood());
    }

    @Override
    public String getName() {
        return "Vanilla";
    }
}
