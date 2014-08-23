package com.fantasycraft.forgepermittor.info.mods;

import com.fantasycraft.forgepermittor.info.IMod;

/**
 * Created by thomas on 8/23/2014.
 */
public class IC2 extends IMod {

    public IC2(){
        addTradeBlockInterface("ic2.core.block.personal.IPersonalBlock");
    }

    @Override
    public String getName() {
        return "IC2";
    }
}
