package com.fantasycraft.forgepermittor.forge;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thomas on 8/20/2014.
 */
public abstract class IMod {

    @Getter
    private List<Class> ContainerInterfaces = new LinkedList<Class>();
    @Getter
    private List<Class> ItemInterFaces = new LinkedList<Class>();
    @Getter
    private List<Class> ConnectablesInterfaces = new LinkedList<Class>();
    @Getter
    private List<Class> ItemBlockContainerInterfaces = new LinkedList<Class>();

    protected void addContainerInterface(String interfaceClass) {
        try {
            this.getContainerInterfaces().add(Class.forName(interfaceClass));
        } catch (ClassNotFoundException e) {}
    }

    @Deprecated
    protected void addItemInterFace(String interfaceClass) {
        try {
            getItemInterFaces().add(Class.forName(interfaceClass));
        } catch (ClassNotFoundException e) {}
    }

    protected void addConnectablesInterface(String interfaceClass)  {
        try {
            getConnectablesInterfaces().add(Class.forName(interfaceClass));
        } catch (ClassNotFoundException e) {}
    }

    protected void addItemBlockContainerInterface(String interfaceClass)  {
        try {
            this.getItemBlockContainerInterfaces().add(Class.forName(interfaceClass));
        } catch (ClassNotFoundException e) {}
    }

    public abstract String getName();


}
