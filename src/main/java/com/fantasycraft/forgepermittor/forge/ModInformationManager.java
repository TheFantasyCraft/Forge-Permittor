package com.fantasycraft.forgepermittor.forge;

import com.fantasycraft.forgepermittor.forge.mods.BuildCraft;
import com.fantasycraft.forgepermittor.nms.NMSResolver;
import lombok.Data;
import org.bukkit.block.Block;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thomas on 8/20/2014.
 */
@Data
public class ModInformationManager {

    List<IMod> Mods;
    NMSResolver nmsResolver;
    public ModInformationManager(NMSResolver nmsResolver){
        this.Mods = new LinkedList<IMod>();
        this.nmsResolver = nmsResolver;

        RegisterMod(new BuildCraft());
    }

    public void RegisterMod(IMod mod){
        Mods.add(mod);
    }

    public Boolean IsConnectable(Block block){
        Object tile = nmsResolver.getCraftWorldHandler().getTileEntityFrom(block);
        if (HasConnectableInferface(tile.getClass()))
            return true;
        return false;
    }

    public boolean HasConnectableInferface(Class OtherClass){
        for (IMod mod : Mods){
                for (Class in : mod.getConnectablesInterfaces()) {
                    if (in.isAssignableFrom(OtherClass))
                        return true;
                }
        }
        return false;
    }

    public boolean HasItemBlockContainerInterface(Class OtherClass){
        for (IMod mod : Mods){
            for (Class in : mod.getItemBlockContainerInterfaces()) {
                if (in.isAssignableFrom(OtherClass))
                    return true;
            }
        }
        return false;
    }

    public boolean HasContainerInterface(Class OtherClass){
        for (IMod mod : Mods){
            for (Class in : mod.getContainerInterfaces()) {
                if (in.isAssignableFrom(OtherClass))
                    return true;
            }
        }
        return false;
    }

}
