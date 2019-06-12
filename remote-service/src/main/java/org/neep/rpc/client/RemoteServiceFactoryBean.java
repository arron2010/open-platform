package org.neep.rpc.client;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @Title ServiceFactoryBean
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author root
 * @Version 1.0.0
 * @Create
 */
public class RemoteServiceFactoryBean<T> implements FactoryBean<T>, InitializingBean {
    private  Class<T> serviceInterface;
    private RemoteServiceRegistry remoteServiceRegistry;
    public RemoteServiceFactoryBean() {

    }

    public RemoteServiceFactoryBean(Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    @Override
    public T getObject() throws Exception {
        return remoteServiceRegistry.<T>getRemoteService(this.serviceInterface );
    }

    @Override
    public Class<?> getObjectType() {
        return  this.serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        remoteServiceRegistry.addRemoteService(this.serviceInterface);
    }

    public RemoteServiceRegistry getRemoteServiceRegistry() {
        return remoteServiceRegistry;
    }

    public void setRemoteServiceRegistry(RemoteServiceRegistry remoteServiceRegistry) {
        this.remoteServiceRegistry = remoteServiceRegistry;
    }
}
