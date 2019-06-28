package org.neep.rpc.bean;

import io.grpc.LoadBalancer;
import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelConfigurer;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelFactory;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import net.devh.boot.grpc.client.interceptor.GlobalClientInterceptorRegistry;
import net.devh.boot.grpc.client.nameresolver.StaticNameResolverProvider;
import org.neep.rpc.client.RemoteClientBeanPostProcessor;
import org.neep.rpc.common.ConfigMappedNameResolverFactoryEx;
import org.neep.rpc.common.ShadedNettyChannelFactoryEx;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * @Title RemoteSeviceConfig
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-15 下午2:43
 */
@Configuration
public class RemoteSeviceConfig {

    @Bean
    public RemoteClientBeanPostProcessor remoteClientBeanPostProcessor( final ApplicationContext applicationContext){
        return new RemoteClientBeanPostProcessor(applicationContext);
    }


    @Bean
    public io.grpc.NameResolver.Factory grpcNameResolverFactory(GrpcChannelsProperties channelProperties) {
        return new ConfigMappedNameResolverFactoryEx(channelProperties, NameResolverProvider.asFactory(),
                StaticNameResolverProvider.STATIC_DEFAULT_URI_MAPPER);
    }

    @Bean
    public GrpcChannelFactory shadedNettyGrpcChannelFactory(final GrpcChannelsProperties properties,
                                                            final LoadBalancer.Factory loadBalancerFactory,
                                                            final NameResolver.Factory nameResolverFactory,
                                                            final GlobalClientInterceptorRegistry globalClientInterceptorRegistry,
                                                            final List<GrpcChannelConfigurer> channelConfigurers) {
        return new ShadedNettyChannelFactoryEx(properties, loadBalancerFactory, nameResolverFactory,
                globalClientInterceptorRegistry, channelConfigurers);
    }

}
