package org.neep.rpc.server;

import io.grpc.MethodDescriptor;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServiceDescriptor;

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

    private MethodDescriptor createMethodDescriptor(){
        return null;
    }

    private ServiceDescriptor createServiceDescriptor(){
        return  null;
    }
}