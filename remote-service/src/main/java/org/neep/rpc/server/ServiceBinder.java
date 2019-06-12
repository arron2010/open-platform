package org.neep.rpc.server;

import io.grpc.ServerServiceDefinition;

/**
 * @Title AbstractBindableService
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-6-6 下午10:45
 */
public class ServiceBinder implements  io.grpc.BindableService{

    private final Object serviceInstance ;

    public ServiceBinder(Object serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    @Override
    public ServerServiceDefinition bindService() {
        System.out.println("ServiceBinder execute ............................");
        return null;
    }
}