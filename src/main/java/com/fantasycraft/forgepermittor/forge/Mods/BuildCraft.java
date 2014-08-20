package com.fantasycraft.forgepermittor.forge.Mods;

import com.fantasycraft.forgepermittor.forge.IMod;

/**
 * Created by thomas on 8/20/2014.
 */
public class BuildCraft extends IMod {
    public BuildCraft() throws ClassNotFoundException {
        addBlockInterface(Class.forName("buildcraft.core.ItemBlockBuildCraft"));

        addConnectablesInterfaces(Class.forName("buildcraft.api.transport.IPipe"));

        addItemInterFaces(Class.forName("buildcraft.core.ItemBuildCraft"));

    }

    @Override
    public String getName() {
        return "BuildCraft";
    }
}
