package com.fantasycraft.forgepermittor.forge;

import lombok.Getter;

import java.util.List;

/**
 * Created by thomas on 8/20/2014.
 */
public abstract class IMod {

    @Getter
    private List<Class> BlockInterfaces;
    @Getter
    private List<Class> ItemInterFaces;
    @Getter
    private List<Class> ConnectablesInterfaces;

    protected void addBlockInterface(Class interfaceClass){
        getBlockInterfaces().add(interfaceClass);
    }

    protected void addItemInterFaces(Class interfaceClass){
        getItemInterFaces().add(interfaceClass);
    }

    protected void addConnectablesInterfaces(Class interfaceClass){
        getConnectablesInterfaces().add(interfaceClass);
    }

    public abstract String getName();

}
