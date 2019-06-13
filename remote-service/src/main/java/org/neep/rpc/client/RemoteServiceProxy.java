package org.neep.rpc.client;



import org.neep.proxy.api.ObjectInvoker;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @Title RemoteServiceProxy
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author root
 * @Version 1.0.0
 * @Create 19-6-10 下午10:58
 */
public class RemoteServiceProxy <T>  implements ObjectInvoker,Serializable {
    private  final Class<T> serviceInterface;

    public RemoteServiceProxy(Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object... arguments) throws Throwable {
        if (method.getName().equals("toString")){
            return proxy.getClass().getInterfaces()[0].getName();
        }
        System.out.println(method.toGenericString()+"..................JDK Proxy");
        return null;
    }
}
