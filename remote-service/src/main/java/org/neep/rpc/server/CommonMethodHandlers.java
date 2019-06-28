package org.neep.rpc.server;

import io.grpc.stub.StreamObserver;
import org.neep.rpc.msg.entity.RpcMsg;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import static org.neep.rpc.common.GrpcHelper.getGrpcMessage;
import static org.neep.rpc.common.GrpcHelper.getMsgTypeValue;
import static org.neep.rpc.common.GrpcHelper.restoreFromGrpcMessage;

/**
 * @Title CommonMethodHandlers
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-6-6 下午10:43
 */
public class CommonMethodHandlers implements
        io.grpc.stub.ServerCalls.UnaryMethod,
        io.grpc.stub.ServerCalls.ServerStreamingMethod,
        io.grpc.stub.ServerCalls.ClientStreamingMethod,
        io.grpc.stub.ServerCalls.BidiStreamingMethod {

    private final Object instance;

    private final Method method;



    public CommonMethodHandlers(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    @Override
    public StreamObserver invoke(StreamObserver streamObserver) {
        return null;
    }

    @Override
    public void invoke(Object o, StreamObserver streamObserver) {
        if (o instanceof  RpcMsg.Message){
            RpcMsg.Message msg = (RpcMsg.Message)o;
            Object entity =restoreFromGrpcMessage(msg);
            Object result =ReflectionUtils.invokeMethod(this.method,this.instance,entity);
            if (result != null){
                String msgType = getMsgTypeValue(result);
                RpcMsg.Message response =  getGrpcMessage(msgType,result);
//                System.out.println(result);
                streamObserver.onNext(response);
                streamObserver.onCompleted();
            }

        }

    }
}
