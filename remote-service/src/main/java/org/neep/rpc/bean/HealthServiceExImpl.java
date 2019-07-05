package org.neep.rpc.bean;


import io.grpc.health.v1.HealthCheckRequest;
import io.grpc.health.v1.HealthCheckResponse;
import io.grpc.health.v1.HealthGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.neep.rpc.anno.EtcdRegistry;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title HealthServiceExImpl
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-2-14 下午11:49
 */
@GrpcService
@EtcdRegistry
public class HealthServiceExImpl extends HealthGrpc.HealthImplBase {

    @Autowired
    private HealthGrpc.HealthImplBase healthGrpc;

    public HealthServiceExImpl() {

       // this.inner =(HealthGrpc.HealthImplBase ) this.manager.getHealthService();
    }

    @Override
    public void check(HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
        this.healthGrpc.check(request,responseObserver);
    }

    @Override
    public void watch(HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
        this.healthGrpc.watch(request,responseObserver);
    }
}
