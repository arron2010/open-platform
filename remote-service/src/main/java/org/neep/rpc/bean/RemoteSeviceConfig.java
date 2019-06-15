package org.neep.rpc.bean;

import io.grpc.LoadBalancer;
import io.grpc.NameResolver;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelConfigurer;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelFactory;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import net.devh.boot.grpc.client.interceptor.GlobalClientInterceptorRegistry;
import org.neep.rpc.client.RemoteClientBeanPostProcessor;
import org.neep.rpc.common.ShadedNettyChannelFactoryEx;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    //@ConditionalOnExpression("${NEED_ETCD:true}")
    public GrpcChannelFactory shadedNettyGrpcChannelFactory(final GrpcChannelsProperties properties,
                                                            final LoadBalancer.Factory loadBalancerFactory,
                                                            final NameResolver.Factory nameResolverFactory,
                                                            final GlobalClientInterceptorRegistry globalClientInterceptorRegistry,
                                                            final List<GrpcChannelConfigurer> channelConfigurers) {
        return new ShadedNettyChannelFactoryEx(properties, loadBalancerFactory, nameResolverFactory,
                globalClientInterceptorRegistry, channelConfigurers);
    }

}
