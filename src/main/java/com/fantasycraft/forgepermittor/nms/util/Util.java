package com.fantasycraft.forgepermittor.nms.util;

import java.lang.reflect.Method;

/**
 * Created by thomas on 8/19/2014.
 */
public class Util {

    public static boolean ClassHasmethodeWithReturnType(Class clasS, Class Returntype){
        for (Method m : clasS.getDeclaredMethods())
            if (m.getReturnType().isAssignableFrom(Returntype)) {
                System.out.println(m);
                return true;
            }
        return false;
    }

}
