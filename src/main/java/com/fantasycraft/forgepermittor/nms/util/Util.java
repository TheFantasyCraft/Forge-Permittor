package com.fantasycraft.forgepermittor.nms.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by thomas on 8/19/2014.
 */
public class Util {

    public static boolean ClassHasmethodeWithReturnType(Class clasS, Class Returntype){
        for (Method m : clasS.getDeclaredMethods())
            if (m.getReturnType().isAssignableFrom(Returntype)) {
                return true;
            }
        return false;
    }

    public static Method getMethode(Class clasS, Class Returntype , int modifier , Class ... classes ){
        for (Method m : clasS.getDeclaredMethods()){
            if (HasParametersignature(m, classes) && m.getReturnType().isAssignableFrom(Returntype) && m.getModifiers() == modifier )
                return m;
        }
        return null;
    }

    private static boolean HasParametersignature(Method method, Class ... classes){
        if (method.getParameterTypes().length == classes.length) {
            for (int i = 0; i < classes.length; i++) {
                if (!classes[i].isAssignableFrom(method.getParameterTypes()[i]))
                    return false;
            }
            return true;
        }
        return false;
    }

    private static boolean hasConstructor(Class clasS,  Class ... classes){
        for (Constructor c : clasS.getDeclaredConstructors()){
            if (c.getParameterTypes().length == classes.length){
                for ( int i = 0 ; i < classes.length ; i++ ) {
                    if (!classes[i].isAssignableFrom(c.getParameterTypes()[i]))
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    public static String stackTraceToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString());
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
