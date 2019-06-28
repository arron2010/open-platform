package org.neep.rpc.common;

import com.google.common.base.Splitter;
import io.grpc.LoadBalancer;
import io.grpc.ManagedChannel;
import io.grpc.NameResolver;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelConfigurer;
import net.devh.boot.grpc.client.channelfactory.ShadedNettyChannelFactory;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import net.devh.boot.grpc.client.interceptor.GlobalClientInterceptorRegistry;

import java.util.Iterator;
import java.util.List;

/**
 * @Title ShadedNettyChannelFactoryEx
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-15 下午5:26
 */
public class ShadedNettyChannelFactoryEx extends ShadedNettyChannelFactory {
    private NameResolver.Factory childNameResolverFactory;
    private LoadBalancer.Factory childLoadBalancerFactory;

    public ShadedNettyChannelFactoryEx(GrpcChannelsProperties properties, LoadBalancer.Factory loadBalancerFactory,
                                       NameResolver.Factory nameResolverFactory,
                                       GlobalClientInterceptorRegistry globalClientInterceptorRegistry,
                                       List<GrpcChannelConfigurer> channelConfigurers) {
        super(properties, loadBalancerFactory, nameResolverFactory, globalClientInterceptorRegistry, channelConfigurers);
        this.childNameResolverFactory=nameResolverFactory;
        this.childLoadBalancerFactory=loadBalancerFactory;
    }
    protected ManagedChannel newManagedChannel(final String name) {

        final NettyChannelBuilder builder = newChannelBuilder(name);
        final  String centerName  = this.getCenterName(name);
        configure(builder, centerName);
        return builder.build();
    }
    private String getCenterName(String name){
        return GrpcHelper.getCenterName(name);
    }

    protected NettyChannelBuilder newChannelBuilder(String name) {
        NettyChannelBuilder nettyChannelBuilder= (NettyChannelBuilder)((NettyChannelBuilder)NettyChannelBuilder.forTarget(name)
                .loadBalancerFactory(this.childLoadBalancerFactory)).nameResolverFactory(this.childNameResolverFactory);
        return nettyChannelBuilder;
    }
}
