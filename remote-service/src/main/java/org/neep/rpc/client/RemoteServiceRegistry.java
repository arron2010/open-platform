package org.neep.rpc.client;

import com.neep.platform.exceptions.RemoteServiceException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title RemoteServiceRegistry
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-6-10 下午11:16
 */
public class RemoteServiceRegistry {

    private final Map<Class<?>, RemoteServiceProxyFactory<?>> knownServices;
    public RemoteServiceRegistry() {
        knownServices = new HashMap<>();
    }

    private  <T> boolean hasMapper(Class<T> type) {
        return this.knownServices.containsKey(type);
    }


    public <T>void addRemoteService(Class<T> type){
        if (type.isInterface()) {
            if (this.hasMapper(type)) {
                throw new RemoteServiceException(type.getName()+"已经被注册");
            }
            this.knownServices.put(type, new RemoteServiceProxyFactory<T>(type));
        }
    }
    public <T> T getRemoteService(Class<T> type){
        RemoteServiceProxyFactory<T> serviceProxyFactory = (RemoteServiceProxyFactory)this.knownServices.get(type);
        if (serviceProxyFactory == null) {
            throw new RemoteServiceException("类型： " + type + " 对于RemoteServiceRegistry是未知类型");
        } else {
            try {
                return serviceProxyFactory.newInstance();
            } catch (Exception ex) {
                throw new RemoteServiceException("获取远程服务对象失败。 原因: " + ex, ex);
            }
        }
    }
}
