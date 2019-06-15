package org.neep.rpc.client;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.MethodDescriptor;
import org.neep.rpc.ext.AbstractStub;

/**
 * @Title ClientProxy
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-14 下午7:39
 */
public class ClientProxy extends AbstractStub {

    public ClientProxy() {

    }

    public ClientProxy(Channel channel) {
        super(channel);
    }

    public ClientProxy(Channel channel, CallOptions callOptions) {
        super(channel, callOptions);
    }

    @Override
    protected AbstractStub build(Channel channel, CallOptions callOptions) {
        return new ClientProxy(channel, callOptions);
    }
//      return blockingUnaryCall(
//            getChannel(), getDoMRMethod(), getCallOptions(), request);
//    public Object call(Object  param){
//
//    }
//
//    public MethodDescriptor getDoMRMethod(){
//        return null;
//    }
}
