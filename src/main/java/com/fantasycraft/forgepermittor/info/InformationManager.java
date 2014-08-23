package com.fantasycraft.forgepermittor.info;

import com.fantasycraft.forgepermittor.info.mods.BuildCraft;
import com.fantasycraft.forgepermittor.info.mods.IC2;
import com.fantasycraft.forgepermittor.info.mods.Vanilla;
import com.fantasycraft.forgepermittor.nms.NMSResolver;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thomas on 8/20/2014.
 */
@Data
public class InformationManager {

    List<IMod> Mods;
    NMSResolver nmsResolver;
    public InformationManager(NMSResolver nmsResolver){
        this.Mods = new LinkedList<IMod>();
        this.nmsResolver = nmsResolver;

        RegisterMod(new Vanilla(nmsResolver));
        RegisterMod(new BuildCraft());
        RegisterMod(new IC2());
    }

    public void RegisterMod(IMod mod){
        Mods.add(mod);
    }


    public boolean HasItemBlockContainerInterface(Object OtherClass){
        return HasItemBlockContainerInterface(OtherClass.getClass());
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

    public boolean HasFoodInterface(Object OtherClass){
        return HasFoodInterface(OtherClass.getClass());
    }

    public boolean HasFoodInterface(Class OtherClass){
        for (IMod mod : Mods){
            for (Class in : mod.getFoodInterfaces()) {
                if (in.isAssignableFrom(OtherClass))
                    return true;
            }
        }
        return false;
    }

    public boolean HasSwordInterface(Object OtherClass){
        return HasSwordInterface(OtherClass.getClass());
    }

    public boolean HasSwordInterface(Class OtherClass){
        for (IMod mod : Mods){
            for (Class in : mod.getWeaponInterfaces()) {
                if (in.isAssignableFrom(OtherClass))
                    return true;
            }
        }
        return false;
    }

    public boolean HasBlockInterface(Object OtherClass){
        return HasBlockInterface(OtherClass.getClass());
    }

    public boolean HasBlockInterface(Class OtherClass){
        for (IMod mod : Mods){
            for (Class in : mod.getBlockInterfaces()) {
                if (in.isAssignableFrom(OtherClass))
                    return true;
            }
        }
        return false;
    }

    public boolean HasContainerInterface(Object OtherClass){
        return HasContainerInterface(OtherClass.getClass());
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

    public boolean HasItemInterface(Object OtherClass) {
        return HasItemInterface(OtherClass.getClass());
    }

    public boolean HasItemInterface(Class OtherClass){
        for (IMod mod : Mods){
            for (Class in : mod.getItemInterFaces()) {
                if (in.isAssignableFrom(OtherClass))
                    return true;
            }
        }
        return false;
    }

    public boolean HasItemBlockInterface(Object OtherClass) {
        return HasItemBlockInterface(OtherClass.getClass());
    }

    public boolean HasItemBlockInterface(Class OtherClass){
        for (IMod mod : Mods){
            for (Class in : mod.getItemBlockInterfaces()) {
                if (in.isAssignableFrom(OtherClass))
                    return true;
            }
        }
        return false;
    }

    public boolean HasTradeBlockInterface(Object OtherClass) {
        return HasItemBlockInterface(OtherClass.getClass());
    }

    public boolean HasTradeBlockInterface(Class OtherClass){
        for (IMod mod : Mods){
            for (Class in : mod.getTradeBlockInterfaces()) {
                if (in.isAssignableFrom(OtherClass))
                    return true;
            }
        }
        return false;
    }
}
