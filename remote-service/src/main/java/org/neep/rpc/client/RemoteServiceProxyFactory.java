package org.neep.rpc.client;


import org.neep.proxy.api.ProxyProvider;
import org.neep.proxy.impl.jdk.JdkProxyProvider;

/**
 * @Title RemoteServiceMethod
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-6-10 下午11:03
 */
public class RemoteServiceProxyFactory<T> {

    private  final Class<T> serviceInterface;
    private  final ProxyProvider proxyProvider;
    public RemoteServiceProxyFactory(Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
        this.proxyProvider = new JdkProxyProvider();
    }
    public T newInstance(){

        T instance =  this.proxyProvider.<T>createInvokerProxy(this.serviceInterface.getClassLoader(),
                new RemoteServiceProxy<T>(this.serviceInterface),  this.serviceInterface);
        return instance;
    }

}
