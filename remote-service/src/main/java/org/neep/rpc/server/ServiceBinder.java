package org.neep.rpc.server;

import io.grpc.MethodDescriptor;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServiceDescriptor;
import io.grpc.stub.ServerCalls;
import org.neep.proxy.api.Invocation;
import org.neep.rpc.anno.RemoteService;
import org.neep.utils.exceptions.RemoteServiceException;
import org.neep.utils.reflect.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.neep.rpc.common.GrpcHelper.getMethodDescriptor;
import static org.neep.rpc.common.GrpcHelper.getServiceName;

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

    private final Invocation invocation;

    public ServiceBinder(Invocation invocation) {
        this.invocation = invocation;
    }

    @Override
    public ServerServiceDefinition bindService() {
        Class serviceInterface = ReflectionHelper.findInterfaceByAnno(invocation.getProxy().getClass(),RemoteService.class);
        if (serviceInterface == null)
            throw new RemoteServiceException("类型"+invocation.getProxy().getClass().getName()+"没有RemoteService注解");

        ServiceDescriptor.Builder serviceBuilder = ServiceDescriptor.newBuilder(getServiceName(serviceInterface));
        List<MethodInfo> methodDescriptorList = this.createMethodDescriptor(serviceInterface);
        for (MethodInfo methodDescriptor : methodDescriptorList){
            serviceBuilder.addMethod(methodDescriptor.getMethodDescriptor());
        }
        ServerServiceDefinition.Builder  builder = ServerServiceDefinition.builder(serviceBuilder.build());

        for (MethodInfo methodDescriptor : methodDescriptorList){
            CommonMethodHandlers handlers = new CommonMethodHandlers(invocation.getProxy(),methodDescriptor.getMethod());
            builder.addMethod(methodDescriptor.getMethodDescriptor(),ServerCalls.asyncUnaryCall(handlers));
        }
        ServerServiceDefinition serverServiceDefinition = builder.build();

      //  System.out.println("ServiceBinder execute ............................");
        return serverServiceDefinition;
    }

//    private String getServiceName(Class<?> serviceInterface){
//        String className = serviceInterface.getName();
//        String[] nameArray = Splitter.on(".").splitToList(className).toArray(new String[]{});
//        return nameArray[nameArray.length-2]+"."+nameArray[nameArray.length-1];
//    }
    private List<MethodInfo> createMethodDescriptor(Class<?> serviceInterface){
        Method[] methods =serviceInterface.getDeclaredMethods();
        List<MethodInfo> methodDescriptorList = new ArrayList<>();
        for (int i=0;i < methods.length;i++){
            methodDescriptorList.add(
                    new MethodInfo(getMethodDescriptor(serviceInterface,methods[i]),methods[i])
                    );
        }
        return methodDescriptorList;
    }

    private class MethodInfo{
        private final MethodDescriptor methodDescriptor;
        private final Method method;

        public MethodInfo(MethodDescriptor methodDescriptor, Method method) {
            this.methodDescriptor = methodDescriptor;
            this.method = method;
        }

        public MethodDescriptor getMethodDescriptor() {
            return methodDescriptor;
        }

        public Method getMethod() {
            return method;
        }
    }
//
//    private ServiceDescriptor createServiceDescriptor( MethodDescriptor methodDescriptor ){
//        return  null;
//    }
}