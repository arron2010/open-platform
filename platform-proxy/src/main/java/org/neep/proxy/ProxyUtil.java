package org.neep.proxy;

import java.lang.reflect.Method;


public abstract class ProxyUtil {




    public static Object[] cloneArray(Object[] args) {
        return args == null ? new Object[0] : args.clone();
    }

}
