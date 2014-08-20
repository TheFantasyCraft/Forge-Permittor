package com.fantasycraft.forgepermittor.info.mods;

import com.fantasycraft.forgepermittor.info.IMod;

/**
 * Created by thomas on 8/20/2014.
 */
public class BuildCraft extends IMod {

    public BuildCraft(){
        addItemBlockContainerInterface("buildcraft.core.IItemPipe");
    }

    @Override
    public String getName() {
        return "BuildCraft";
    }
}
