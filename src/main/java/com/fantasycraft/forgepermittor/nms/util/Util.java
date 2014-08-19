package com.fantasycraft.forgepermittor.nms.util;

import java.lang.reflect.Method;

/**
 * Created by thomas on 8/19/2014.
 */
public class Util {

    public static boolean ClassHasmethodeWithReturnType(Class clasS, Class Returntype, Class SuperClass){
        for (Method m : clasS.getDeclaredMethods())
            if (m.getReturnType().isAssignableFrom(Returntype) /*&& !m.getClass().getName().equalsIgnoreCase(SuperClass.getName())*/) {
                System.out.println(m);
                return true;
            }
        return false;
    }

}
