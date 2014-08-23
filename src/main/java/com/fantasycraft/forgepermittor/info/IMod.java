package com.fantasycraft.forgepermittor.info;

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
    private List<Class> BlockInterfaces = new LinkedList<Class>();
    @Getter
    private List<Class> ItemBlockContainerInterfaces = new LinkedList<Class>();
    @Getter
    private List<Class> FoodInterfaces = new LinkedList<Class>();
    @Getter
    private List<Class> WeaponInterfaces = new LinkedList<Class>();
    @Getter
    private List<Class> ItemBlockInterfaces = new LinkedList<Class>();
    @Getter
    private List<Class> TradeBlockInterfaces = new LinkedList<Class>();



    protected void addContainerInterface(String interfaceClass) {
        try {
            this.addContainerInterface(Class.forName(interfaceClass));
        } catch (ClassNotFoundException e) {}
    }

    protected void addContainerInterface(Class interfaceClass) {
            this.getContainerInterfaces().add(interfaceClass);
    }

    protected void addItemInterFace(String interfaceClass) {
        try {
            addItemInterFace(Class.forName(interfaceClass));
        } catch (ClassNotFoundException e) {}
    }

    protected void addItemInterFace(Class interfaceClass) {
            getItemInterFaces().add(interfaceClass);
    }


    protected void addItemBlockContainerInterface(String interfaceClass)  {
        try {
            this.addItemBlockContainerInterface(Class.forName(interfaceClass));
        } catch (ClassNotFoundException e) {}
    }

    protected void addItemBlockContainerInterface(Class interfaceClass)  {
            this.getItemBlockContainerInterfaces().add(interfaceClass);
    }


    protected void addBlockInterface(String interfaceClass)  {
        try {
            this.addBlockInterface(Class.forName(interfaceClass));
        } catch (ClassNotFoundException e) {}
    }

    protected void addBlockInterface(Class interfaceClass)  {
        this.getBlockInterfaces().add(interfaceClass);
    }

    protected void addFoodInterface(String interfaceClass)  {
        try {
            this.addFoodInterface(Class.forName(interfaceClass));
        } catch (ClassNotFoundException e) {}
    }

    protected void addFoodInterface(Class interfaceClass)  {
        this.getFoodInterfaces().add(interfaceClass);
    }

    protected void addWeaponInterface(String interfaceClass) {
        try {
            this.addWeaponInterface(Class.forName(interfaceClass));
        } catch (ClassNotFoundException e) {}
    }

    protected void addWeaponInterface(Class interfaceClass) {
        this.getWeaponInterfaces().add(interfaceClass);
    }

    protected void addItemBlockInterface(String interfaceClass) {
        try {
            this.addItemBlockInterface(Class.forName(interfaceClass));
        } catch (ClassNotFoundException e) {}
    }

    protected void addItemBlockInterface(Class interfaceClass) {
        this.getItemBlockInterfaces().add(interfaceClass);
    }

    protected void addTradeBlockInterface(String interfaceClass) {
        try {
            this.addItemBlockInterface(Class.forName(interfaceClass));
        } catch (ClassNotFoundException e) {}
    }

    protected void addTradeBlockInterface(Class interfaceClass) {
        this.getTradeBlockInterfaces().add(interfaceClass);
    }

    public abstract String getName();

}
