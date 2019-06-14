package org.neep.rpc.client;



import io.grpc.BindableService;
import io.grpc.MethodDescriptor;
import org.neep.proxy.api.ObjectInvoker;
import org.neep.rpc.msg.entity.RpcMsg;
import org.neep.utils.exceptions.RemoteServiceException;
import org.neep.utils.reflect.ReflectionHelper;

import java.io.Serializable;
import java.lang.reflect.Method;

import static io.grpc.stub.ClientCalls.blockingUnaryCall;

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
        if (arguments == null ||  arguments.length != 1 ){
            throw new RemoteServiceException(method.toGenericString()+"参数只能为一个");
        }
        boolean found = ReflectionHelper.existMethod(method,  this.serviceInterface);
        if (!found){
            throw new RemoteServiceException("未找到服务"+method.toGenericString());
        }
        ClientProxy clientProxy = (ClientProxy)proxy;
        RpcMsg.Message response = (RpcMsg.Message)blockingUnaryCall(clientProxy.getChannel(),this.createMethodDescriptor(),clientProxy.getCallOptions(),this.createRpcMsg(arguments[0]));
        if (response.getResult()){
            Object objResult = this.getReturnObject(response);
            return objResult;
        }else{
            throw new RemoteServiceException(method.toGenericString()+"远程调用失败。原因："+response.getResultText());
        }
    }
    private MethodDescriptor createMethodDescriptor(){
        return null;
    }

    private RpcMsg.Message createRpcMsg(Object source){
        return null;
    }

    private RpcMsg getReturnObject( RpcMsg.Message  response){
        return null;
    }
}
