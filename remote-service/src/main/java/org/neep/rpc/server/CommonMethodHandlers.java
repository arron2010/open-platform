package org.neep.rpc.server;

import io.grpc.stub.StreamObserver;

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

    @Override
    public StreamObserver invoke(StreamObserver streamObserver) {
        return null;
    }

    @Override
    public void invoke(Object o, StreamObserver streamObserver) {

    }
}
