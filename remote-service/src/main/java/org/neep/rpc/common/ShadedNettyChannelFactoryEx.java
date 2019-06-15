package org.neep.rpc.common;

import io.grpc.LoadBalancer;
import io.grpc.NameResolver;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelConfigurer;
import net.devh.boot.grpc.client.channelfactory.ShadedNettyChannelFactory;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import net.devh.boot.grpc.client.interceptor.GlobalClientInterceptorRegistry;

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
    public ShadedNettyChannelFactoryEx(GrpcChannelsProperties properties, LoadBalancer.Factory loadBalancerFactory,
                                       NameResolver.Factory nameResolverFactory,
                                       GlobalClientInterceptorRegistry globalClientInterceptorRegistry,
                                       List<GrpcChannelConfigurer> channelConfigurers) {
        super(properties, loadBalancerFactory, nameResolverFactory, globalClientInterceptorRegistry, channelConfigurers);
    }
}
