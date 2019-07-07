package org.neep.rpc.bean;

import io.etcd.jetcd.Client;
import io.grpc.LoadBalancer;
import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import io.grpc.health.v1.HealthGrpc;
import io.grpc.services.HealthStatusManager;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelConfigurer;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelFactory;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import net.devh.boot.grpc.client.interceptor.GlobalClientInterceptorRegistry;
import net.devh.boot.grpc.client.nameresolver.StaticNameResolverProvider;
import net.devh.boot.grpc.server.config.GrpcServerProperties;
import org.neep.etcd.bean.EtcdProperties;
import org.neep.etcd.registry.EtcdHeartbeatLease;
import org.neep.etcd.registry.EtcdServiceRegistry;
import org.neep.etcd.resolver.EtcdNameResolverFactory;
import org.neep.rpc.client.RemoteClientBeanPostProcessor;
import org.neep.rpc.client.ConfigMappedNameResolverFactoryEx;
import org.neep.rpc.common.ServiceConfigResourceLoader;
import org.neep.rpc.common.ShadedNettyChannelFactoryEx;
import org.neep.rpc.server.EtcdAutoRegistrant;
import org.neep.rpc.server.RemoteServerBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * @Title RemoteSeviceConfig
 * @Description spring bean的构建
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-15 下午2:43
 */
@Configuration
@PropertySource(value = { "classpath:etcd.properties" })
@EnableConfigurationProperties(EtcdProperties.class)
public class RemoteSeviceConfig {

    @Bean
    @ConditionalOnExpression("${NEED_ETCD:true}")
    public Client etcdClient(EtcdProperties properties) {
        String endpoints = properties.getEndpoints();
        return Client.builder()
                .endpoints(endpoints)
                .build();
    }
    @Bean
    @ConditionalOnExpression("${NEED_ETCD:true}")
    public EtcdHeartbeatLease heartbeatLease(Client etcdClient, EtcdProperties properties) {
        return new EtcdHeartbeatLease(etcdClient, properties);
    }

    @Bean
    @ConditionalOnExpression("${NEED_ETCD:true}")
    public RemoteServerBeanPostProcessor remoteServerBeanPostProcessor(final EtcdAutoRegistrant etcdAutoRegistrant){
        return new RemoteServerBeanPostProcessor(etcdAutoRegistrant);
    }

    @Bean
    @ConditionalOnExpression("${NEED_ETCD:true}")
    public EtcdServiceRegistry etcdServiceRegistry(Client etcdClient, EtcdProperties properties, EtcdHeartbeatLease etcdHeartbeatLease) {
        return new EtcdServiceRegistry(etcdClient,properties, etcdHeartbeatLease);
    }

    @Bean
    @ConditionalOnExpression("${NEED_ETCD:true}")
    public EtcdAutoRegistrant etcdAutoRegistrant(EtcdServiceRegistry etcdServiceRegistry, GrpcChannelsProperties properties,
                                                 HealthStatusManager healthStatusManager, GrpcServerProperties grpcServerProperties){

        EtcdAutoRegistrant etcdAutoRegistrant = null;
        etcdAutoRegistrant = new EtcdAutoRegistrant(etcdServiceRegistry,properties,healthStatusManager,grpcServerProperties);
        return etcdAutoRegistrant;
    }

    @Bean
    @ConditionalOnExpression("${NEED_ETCD:true}")
    public NameResolver.Factory etcdGrpcNameResolverFactory(final EtcdProperties etcdProperties, final Client etcdClient) {
        return new EtcdNameResolverFactory(etcdProperties, etcdClient);
    }

    @Bean
    @ConditionalOnExpression("${NEED_ETCD}==false")
    public io.grpc.NameResolver.Factory staticGrpcNameResolverFactory(GrpcChannelsProperties channelProperties) {
        return new ConfigMappedNameResolverFactoryEx(channelProperties, NameResolverProvider.asFactory(),
                StaticNameResolverProvider.STATIC_DEFAULT_URI_MAPPER);
    }

    @Bean
    public RemoteClientBeanPostProcessor remoteClientBeanPostProcessor( final ApplicationContext applicationContext){
        return new RemoteClientBeanPostProcessor(applicationContext);
    }

    @Bean
    public ServiceConfigResourceLoader serviceConfigResourceLoader(){
        return new ServiceConfigResourceLoader();
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

    @Bean
    public HealthStatusManager healthStatusManager(){
        return new HealthStatusManager();
    }

    @Bean
    public HealthGrpc.HealthImplBase healthGrpc(HealthStatusManager healthStatusManager){
        return (HealthGrpc.HealthImplBase ) healthStatusManager.getHealthService();
    }







}
