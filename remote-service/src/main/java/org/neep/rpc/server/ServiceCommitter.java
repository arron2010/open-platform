package org.neep.rpc.server;


import org.neep.proxy.api.Invocation;

/**
 * @Title ServiceCommitter
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-6-8 下午5:56
 */
public class ServiceCommitter {
    private final Invocation invocation;

    public ServiceCommitter(Invocation invocation) {
        this.invocation = invocation;
    }

    public Object commit() throws  Throwable{
        Object result = invocation.proceed();
        System.out.println("ServiceCommitter execute..........");
        return result;
    }
}
