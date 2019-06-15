package org.neep.rpc.client;


import io.grpc.MethodDescriptor;
import org.neep.proxy.api.ObjectInvoker;
import org.neep.rpc.msg.anno.MessageType;
import org.neep.rpc.msg.entity.MsgTypeConstants;
import org.neep.rpc.msg.entity.RemoteEntity;
import org.neep.rpc.msg.entity.RpcMsg;
import org.neep.utils.exceptions.RemoteServiceException;
import org.neep.utils.reflect.ReflectionHelper;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.Serializable;
import java.lang.reflect.Method;

import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static org.neep.rpc.common.GrpcHelper.*;
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
        String msgType = this.getMsgTypeValue(arguments[0]);
        RpcMsg.Message request =   this.createRpcMsg(msgType,arguments[0]);

        RpcMsg.Message response = (RpcMsg.Message)blockingUnaryCall(clientProxy.getChannel(),
                this.createMethodDescriptor(method),
                clientProxy.getCallOptions(),
                request);
        Object objResult = this.getReturnObject(response);
        if (objResult instanceof RemoteEntity){
            RemoteEntity remoteEntity = (RemoteEntity)objResult;
            if (remoteEntity.isSuccess()){
                return objResult;
            }else{
                throw new RemoteServiceException(method.toGenericString()+"远程调用失败。原因："+remoteEntity.getMessage());
            }
        }
        return objResult;
    }
    private String getMsgTypeValue(Object  inputObj ){
        MessageType messageType= AnnotationUtils.getAnnotation(inputObj.getClass(),MessageType.class);
        if (messageType != null){
            return  messageType.value();
        }else{
            return MsgTypeConstants.PROTOBUF;
        }
    }

    private MethodDescriptor createMethodDescriptor(Method method) throws RemoteServiceException{
        return getMethodDescriptor(this.serviceInterface,method);
    }

    private synchronized RpcMsg.Message createRpcMsg(String msgType,Object source)  throws RemoteServiceException{
        return getGrpcMessage(msgType,source);
    }

    private synchronized Object getReturnObject( RpcMsg.Message  response){
        return restoreFromGrpcMessage(response);
    }
}
