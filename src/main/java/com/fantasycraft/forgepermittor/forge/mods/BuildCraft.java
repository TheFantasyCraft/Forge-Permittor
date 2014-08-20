package com.fantasycraft.forgepermittor.forge.mods;

import com.fantasycraft.forgepermittor.forge.IMod;

/**
 * Created by thomas on 8/20/2014.
 */
public class BuildCraft extends IMod {
    public BuildCraft(){

        //Connectables can be Items Or Blocks
        addConnectablesInterface("buildcraft.api.transport.IPipeConnection");
        addConnectablesInterface("buildcraft.core.IItemPipe");
    }

    @Override
    public String getName() {
        return "BuildCraft";
    }
}
